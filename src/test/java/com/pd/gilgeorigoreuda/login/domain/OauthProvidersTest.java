package com.pd.gilgeorigoreuda.login.domain;

import com.pd.gilgeorigoreuda.login.exception.NotSupportedOauthServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@MockitoSettings
class OauthProvidersTest {

    private static final String KAKAO_PROVIDER_NAME = "kakao";
    private static final String NAVER_PROVIDER_NAME = "naver";
    private static final String NOT_EXISTS_PROVIDER_NAME = "notExists";

    @InjectMocks
    private OauthProviders oauthProviders;

    @Mock
    private OauthProvider mockKakaoProvider;

    @Mock
    private OauthProvider mockNaverProvider;

    @Test
    @DisplayName("해당 제공자명이 있는 OauthProvider 구현 클래스가 존재하는 경우 해당 OauthProvider 반환")
    void whenKakaoProviderNameExists() {
        // given
        List<OauthProvider> providers = List.of(mockKakaoProvider, mockNaverProvider);
        oauthProviders = new OauthProviders(providers);

        // when
        when(mockKakaoProvider.is(KAKAO_PROVIDER_NAME)).thenReturn(true);
        OauthProvider provider = oauthProviders.mapping(KAKAO_PROVIDER_NAME);

        // then
        assertThat(provider).isEqualTo(mockKakaoProvider);
    }

    @Test
    @DisplayName("해당 제공자명이 있는 OauthProvider 구현 클래스가 존재하는 경우 해당 OauthProvider 반환")
    void whenNaverProviderNameExists() {
        // given
        List<OauthProvider> providers = List.of(mockKakaoProvider, mockNaverProvider);
        oauthProviders = new OauthProviders(providers);

        // when
        when(mockNaverProvider.is(NAVER_PROVIDER_NAME)).thenReturn(true);
        OauthProvider provider = oauthProviders.mapping(NAVER_PROVIDER_NAME);

        // then
        assertThat(provider).isEqualTo(mockNaverProvider);
    }

    @Test
    @DisplayName("해당 제공자명이 있는 OauthProvider 구현 클래스가 존재하지 않는 경우 NotSupportedOauthServiceException 발생")
    void whenProviderNameNotExists() {
        // given
        List<OauthProvider> providers = List.of(mockKakaoProvider, mockNaverProvider);
        oauthProviders = new OauthProviders(providers);

        // when & then
        assertThatThrownBy(() -> oauthProviders.mapping(NOT_EXISTS_PROVIDER_NAME))
                .isInstanceOf(NotSupportedOauthServiceException.class);
    }

}