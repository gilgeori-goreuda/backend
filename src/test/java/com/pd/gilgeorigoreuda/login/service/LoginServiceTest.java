package com.pd.gilgeorigoreuda.login.service;

import com.pd.gilgeorigoreuda.login.domain.MemberAccessRefreshToken;
import com.pd.gilgeorigoreuda.login.domain.MemberToken;
import com.pd.gilgeorigoreuda.login.domain.OauthProvider;
import com.pd.gilgeorigoreuda.login.domain.OauthUserInfo;
import com.pd.gilgeorigoreuda.login.exception.InvalidAccessTokenException;
import com.pd.gilgeorigoreuda.login.exception.RenewalAccessTokenFailException;
import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.member.domain.entity.MemberActiveInfo;
import com.pd.gilgeorigoreuda.settings.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

import static com.pd.gilgeorigoreuda.settings.fixtures.MemberActiveInfoFixtures.*;
import static com.pd.gilgeorigoreuda.settings.fixtures.MemberFixtures.*;
import static com.pd.gilgeorigoreuda.settings.fixtures.MemberTokenFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;


class LoginServiceTest extends ServiceTest {

    private final OauthProvider someOauthProvider = mock(OauthProvider.class);
    private final OauthUserInfo someOauthUserInfo = mock(OauthUserInfo.class);

    @Test
    @DisplayName("제공자 이름과 코드를 받아서 로그인을 수행 - 회원정보가 없는 경우 회원가입 후 로그인")
    void whenSocialIdIsNullThenCreateMemberAndLogin() {
        // given
        given(oauthProviders.mapping(anyString())).willReturn(someOauthProvider);
        given(someOauthProvider.getUserInfo(anyString())).willReturn(someOauthUserInfo);
        given(someOauthUserInfo.getSocialId()).willReturn("some socialId");
        given(someOauthUserInfo.getNickname()).willReturn("some nickname");
        given(someOauthUserInfo.getProfileImageUrl()).willReturn("some profileImageUrl");

        given(memberRepository.findBySocialId(anyString())).willReturn(Optional.empty());
        given(memberRepository.save(any(Member.class))).willReturn(KIM());

        given(memberActiveInfoRepository.findByMemberId(anyLong())).willReturn(Optional.empty());
        given(memberActiveInfoRepository.save(any(MemberActiveInfo.class))).willReturn(KIM_ACTIVE_INFO());

        given(jwtProvider.generateLoginToken(anyString())).willReturn(mock(MemberAccessRefreshToken.class));

        // when
        MemberAccessRefreshToken memberAccessRefreshToken = loginService.login("providerName", "code");

        // then
        assertAll(
                () -> then(memberRepository).should(times(1)).findBySocialId(anyString()),
                () -> then(memberRepository).should(times(1)).save(any(Member.class)),
                () -> then(memberActiveInfoRepository).should(times(1)).findByMemberId(anyLong()),
                () -> then(memberActiveInfoRepository).should(times(1)).save(any(MemberActiveInfo.class)),
                () -> then(memberTokenRepository).should(times(1)).save(any(MemberToken.class)),
                () -> assertThat(memberAccessRefreshToken).isNotNull()
        );
    }

    @Test
    @DisplayName("제공자 이름과 코드를 받아서 로그인을 수행 - 회원정보가 있는 경우 로그인")
    void whenSocialIdIsNotNullThenLogin() {
        // given
        given(oauthProviders.mapping(anyString())).willReturn(someOauthProvider);
        given(someOauthProvider.getUserInfo(anyString())).willReturn(someOauthUserInfo);
        given(someOauthUserInfo.getSocialId()).willReturn("some socialId");

        given(memberRepository.findBySocialId(anyString())).willReturn(Optional.of(KIM()));
        given(memberActiveInfoRepository.findByMemberId(anyLong())).willReturn(Optional.of(KIM_ACTIVE_INFO()));

        given(jwtProvider.generateLoginToken(anyString())).willReturn(mock(MemberAccessRefreshToken.class));

        // when
        MemberAccessRefreshToken memberAccessRefreshToken = loginService.login("providerName", "code");

        // then
        assertAll(
                () -> then(memberRepository).should(times(1)).findBySocialId(anyString()),
                () -> then(memberRepository).should(never()).save(any(Member.class)),
                () -> then(memberActiveInfoRepository).should(times(1)).findByMemberId(anyLong()),
                () -> then(memberActiveInfoRepository).should(never()).save(any(MemberActiveInfo.class)),
                () -> then(memberTokenRepository).should(times(1)).save(any(MemberToken.class)),
                () -> assertThat(memberAccessRefreshToken).isNotNull()
        );
    }

    @Nested
    @DisplayName("AccessToken 재발급")
    class regenerateAccessToken {

        private static final String AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION;
        private static final String VALID_ACCESS_TOKEN = "ValidAccessToken";
        private static final String INVALID_ACCESS_TOKEN = "InvalidAccessToken";
        private static final String NEW_ACCESS_TOKEN = "NewAccessToken";

        @Test
        @DisplayName("AccessToken 재발급 - RefreshToken 이 유효하지만 AccessToken 기간 만료인 경우")
        void whenRefreshTokenIsValidThenRegenerateAccessToken() {
            // given
            given(bearerExtractor.extractAccessToken(AUTHORIZATION_HEADER)).willReturn(VALID_ACCESS_TOKEN);
            given(memberTokenRepository.findByAccessToken(anyString())).willReturn(Optional.of(KIM_TOKEN()));
            given(jwtProvider.isValidRefreshButInvalidAccessToken(KIM_TOKEN().getRefreshToken(), KIM_TOKEN().getAccessToken())).willReturn(true);
            given(jwtProvider.regenerateAccessToken(KIM_TOKEN().getMemberId().toString())).willReturn(NEW_ACCESS_TOKEN);

            // when
            String accessToken = loginService.regenerateAccessToken(AUTHORIZATION_HEADER);

            // then
            assertAll(
                    () -> then(bearerExtractor).should(times(1)).extractAccessToken(AUTHORIZATION_HEADER),
                    () -> then(memberTokenRepository).should(times(1)).findByAccessToken(anyString()),
                    () -> then(jwtProvider).should(times(1)).isValidRefreshButInvalidAccessToken(anyString(), anyString()),
                    () -> then(jwtProvider).should(times(1)).regenerateAccessToken(anyString()),
                    () -> then(memberTokenRepository).should(times(1)).updateAccessToken(KIM_TOKEN().getMemberId(), NEW_ACCESS_TOKEN),
                    () -> assertThat(accessToken).isNotNull()
            );
        }

        @Test
        @DisplayName("AccessToken 재발급 - RefreshToken, AccessToken 모두 유효한 경우")
        void whenRefreshTokenAndAccessTokenAreValidThenReturnAccessToken() {
            // given
            given(bearerExtractor.extractAccessToken(AUTHORIZATION_HEADER)).willReturn(VALID_ACCESS_TOKEN);
            given(memberTokenRepository.findByAccessToken(anyString())).willReturn(Optional.of(KIM_TOKEN()));
            given(jwtProvider.isValidRefreshAndValidAccessToken(KIM_TOKEN().getRefreshToken(), KIM_TOKEN().getAccessToken())).willReturn(true);

            // when
            String accessToken = loginService.regenerateAccessToken(AUTHORIZATION_HEADER);

            // then
            assertAll(
                    () -> then(bearerExtractor).should(times(1)).extractAccessToken(AUTHORIZATION_HEADER),
                    () -> then(memberTokenRepository).should(times(1)).findByAccessToken(anyString()),
                    () -> then(jwtProvider).should(times(1)).isValidRefreshAndValidAccessToken(anyString(), anyString()),
                    () -> then(memberTokenRepository).should(never()).updateAccessToken(anyLong(), anyString()),
                    () -> assertThat(accessToken).isNotNull()
            );
        }

        @Test
        @DisplayName("AccessToken 재발급 - RefreshToken이 유효하지 않은 경우 AccessToken 갱신 실패")
        void whenRefreshTokenIsInvalidThenThrowException() {
            // given
            given(bearerExtractor.extractAccessToken(AUTHORIZATION_HEADER)).willReturn(INVALID_ACCESS_TOKEN);
            given(memberTokenRepository.findByAccessToken(anyString())).willReturn(Optional.of(KIM_TOKEN()));
            given(jwtProvider.isValidRefreshButInvalidAccessToken(KIM_TOKEN().getRefreshToken(), KIM_TOKEN().getAccessToken())).willReturn(false);
            given(jwtProvider.isValidRefreshAndValidAccessToken(KIM_TOKEN().getRefreshToken(), KIM_TOKEN().getAccessToken())).willReturn(false);

            // when & then
            assertThatThrownBy(() -> loginService.regenerateAccessToken(AUTHORIZATION_HEADER))
                    .isInstanceOf(RenewalAccessTokenFailException.class);
        }

        @Test
        @DisplayName("AccessToken 재발급 - 저장된 AccessToken이 없는 경우 잘못된 AccessToken 예외 발생")
        void whenAccessTokenIsNotFoundThenThrowException() {
            // given
            given(bearerExtractor.extractAccessToken(AUTHORIZATION_HEADER)).willReturn(INVALID_ACCESS_TOKEN);
            given(memberTokenRepository.findByAccessToken(anyString())).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> loginService.regenerateAccessToken(AUTHORIZATION_HEADER))
                    .isInstanceOf(InvalidAccessTokenException.class);
        }
    }

    @Test
    @DisplayName("로그아웃 - AccessToken 삭제")
    void deleteMemberToken() {
        // given
        given(bearerExtractor.extractAccessToken(anyString())).willReturn(KIM_TOKEN().getAccessToken());

        // when
        loginService.deleteMemberToken(anyString());

        // then
        then(memberTokenRepository).should(times(1)).deleteByAccessToken(KIM_TOKEN().getAccessToken());
    }

}