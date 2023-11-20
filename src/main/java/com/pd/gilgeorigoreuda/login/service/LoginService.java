package com.pd.gilgeorigoreuda.login.service;

import com.pd.gilgeorigoreuda.login.domain.*;
import com.pd.gilgeorigoreuda.login.exception.InvalidAccessTokenException;
import com.pd.gilgeorigoreuda.login.exception.RenewalAccessTokenFailException;
import com.pd.gilgeorigoreuda.login.jwt.BearerTokenExtractor;
import com.pd.gilgeorigoreuda.login.jwt.JwtProvider;
import com.pd.gilgeorigoreuda.login.repository.MemberTokenRepository;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final MemberTokenRepository memberTokenRepository;
    private final MemberRepository memberRepository;
    private final OauthProviders oauthProviders;
    private final JwtProvider jwtProvider;
    private final BearerTokenExtractor bearerExtractor;

    public MemberAccessRefreshToken login(final String providerName, final String code) {
        OauthProvider provider = oauthProviders.mapping(providerName);
        OauthUserInfo userInfo = provider.getUserInfo(code);

        log.info("socialId : {}", userInfo.getSocialId());
        log.info("nickname : {}", userInfo.getNickname());
        log.info("profileImageUrl : {}", userInfo.getProfileImageUrl());

        Member member = findMemberOrElseCreateMember(
                userInfo.getSocialId(),
                userInfo.getNickname(),
                userInfo.getProfileImageUrl()
        );

        MemberAccessRefreshToken memberAccessRefreshToken = jwtProvider.generateLoginToken(member.getId().toString());

        MemberToken memberToken = MemberToken.builder()
                        .memberId(member.getId())
                        .accessToken(memberAccessRefreshToken.getAccessToken())
                        .refreshToken(memberAccessRefreshToken.getRefreshToken())
                        .build();

        memberTokenRepository.save(memberToken);

        return memberAccessRefreshToken;
    }

    private Member findMemberOrElseCreateMember(final String socialId, final String nickname, final String profileImageUrl) {
        return memberRepository.findBySocialId(socialId)
                .orElseGet(() -> createMember(socialId, nickname, profileImageUrl));
    }

    private Member createMember(final String socialId, final String nickname, final String profileImageUrl) {
        if (!memberRepository.existsByNickname(nickname)) {
            return memberRepository.save(
                    Member.builder()
                            .nickname(nickname)
                            .profileImageUrl(profileImageUrl)
                            .socialId(socialId)
                            .build()
            );
        }

        return memberRepository.save(
                Member.builder()
                        .nickname(nickname + "/" + socialId)
                        .profileImageUrl(profileImageUrl)
                        .socialId(socialId)
                        .build()
        );
    }

    public String renewalAccessToken(final String authorizationHeader) {
        MemberToken memberToken = getMemberTokenByAccessToken(authorizationHeader);

        String accessToken = memberToken.getAccessToken();
        String refreshToken = memberToken.getRefreshToken();

        if (jwtProvider.isValidRefreshButInvalidAccessToken(refreshToken, accessToken)) {
            return jwtProvider.regenerateAccessToken(memberToken.getMemberId().toString());
        }

        if (jwtProvider.isValidRefreshAndValidAccessToken(refreshToken, accessToken)) {
            return memberToken.getAccessToken();
        }

        throw new RenewalAccessTokenFailException();
    }

    private MemberToken getMemberTokenByAccessToken(final String authorizationHeader) {
        String extractedAccessToken = getExtractedAccessToken(authorizationHeader);

        return memberTokenRepository.findByAccessToken(extractedAccessToken)
                .orElseThrow(InvalidAccessTokenException::new);
    }

    private String getExtractedAccessToken(final String authorizationHeader) {
        return bearerExtractor.extractAccessToken(authorizationHeader);
    }

    public void deleteMemberToken(final String authorizationHeader) {
        String extractedAccessToken = getExtractedAccessToken(authorizationHeader);
        memberTokenRepository.deleteByAccessToken(extractedAccessToken);
    }

}
