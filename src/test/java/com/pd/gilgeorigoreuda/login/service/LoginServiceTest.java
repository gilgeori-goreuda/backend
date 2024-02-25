package com.pd.gilgeorigoreuda.login.service;

import com.pd.gilgeorigoreuda.login.domain.MemberAccessRefreshToken;
import com.pd.gilgeorigoreuda.login.domain.MemberToken;
import com.pd.gilgeorigoreuda.login.domain.OauthProvider;
import com.pd.gilgeorigoreuda.login.domain.OauthUserInfo;
import com.pd.gilgeorigoreuda.login.provider.KakaoOauthProvider;
import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.member.domain.entity.MemberActiveInfo;
import com.pd.gilgeorigoreuda.settings.ServiceTest;
import com.pd.gilgeorigoreuda.settings.fixtures.MemberActiveInfoFixtures;
import com.pd.gilgeorigoreuda.settings.fixtures.MemberFixtures;
import com.pd.gilgeorigoreuda.settings.fixtures.MemberTokenFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.pd.gilgeorigoreuda.settings.fixtures.MemberActiveInfoFixtures.*;
import static com.pd.gilgeorigoreuda.settings.fixtures.MemberFixtures.*;
import static com.pd.gilgeorigoreuda.settings.fixtures.MemberTokenFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.*;
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

}