package com.pd.gilgeorigoreuda.login.service;

import com.pd.gilgeorigoreuda.login.domain.*;
import com.pd.gilgeorigoreuda.login.exception.InvalidRefreshTokenException;
import com.pd.gilgeorigoreuda.login.exception.RenewalAccessTokenFailException;
import com.pd.gilgeorigoreuda.login.jwt.BearerTokenExtractor;
import com.pd.gilgeorigoreuda.login.jwt.JwtProvider;
import com.pd.gilgeorigoreuda.login.repository.RefreshTokenRepository;

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

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final OauthProviders oauthProviders;
    private final JwtProvider jwtProvider;
    private final BearerTokenExtractor bearerExtractor;


    public MemberToken login(final String providerName, final String code) {
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

        MemberToken memberToken = jwtProvider.generateLoginToken(member.getId().toString());
        RefreshToken refreshToken = RefreshToken.builder()
                .token(memberToken.getRefreshToken())
                .memberId(member.getId())
                .build();

        refreshTokenRepository.save(refreshToken);

        return memberToken;
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

    public String renewalAccessToken(final String refreshTokenRequest, final String authorizationHeader) {
        String accessToken = bearerExtractor.extractAccessToken(authorizationHeader);

        if (jwtProvider.isValidRefreshButInvalidAccessToken(refreshTokenRequest, accessToken)) {
            RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenRequest)
                    .orElseThrow(InvalidRefreshTokenException::new);

            return jwtProvider.regenerateAccessToken(refreshToken.getMemberId().toString());
        }

        if (jwtProvider.isValidRefreshAndValidAccessToken(refreshTokenRequest, accessToken)) {
            return accessToken;
        }

        throw new RenewalAccessTokenFailException();
    }

    public void removeRefreshToken(final String refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
    }

}
