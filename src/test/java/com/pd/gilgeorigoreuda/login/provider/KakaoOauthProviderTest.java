package com.pd.gilgeorigoreuda.login.provider;

import com.pd.gilgeorigoreuda.login.domain.OauthAccessToken;
import com.pd.gilgeorigoreuda.login.domain.OauthUserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@MockitoSettings
class KakaoOauthProviderTest {

    @InjectMocks
    private KakaoOauthProvider kakaoOauthProvider;

    @Mock
    private RestTemplate restTemplate;

    private static final String TEST_CODE = "test_code";
    private static final String TEST_ACCESS_TOKEN = "test access token";
    private static final String TEST_CLIENT_ID = "dd83fb5281e50c8508ffc20b8dc07799/test";
    private static final String TEST_REDIRECT_URL = "http://localhost:3000/oauth2/callback/test/kakao";
    private static final String TEST_TOKEN_URL = "https://kauth.kakao.com/oauth/test/token";
    private static final String TEST_USER_INFO_URL = "https://kapi.kakao.com/v2/user/test/me";

    private static final OauthAccessToken mockOauthAccessToken = mock(OauthAccessToken.class);

    private HttpEntity<MultiValueMap<String, String>> setRequestAccessTokenHeaderAndBody(final String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", TEST_CODE);
        body.add("client_id", TEST_CLIENT_ID);
        body.add("redirect_uri", TEST_REDIRECT_URL);
        body.add("grant_type", "authorization_code");

        return new HttpEntity<>(body, headers);
    }

    private ResponseEntity<OauthUserInfo> getUserInfo() {
        return restTemplate.exchange(
                TEST_USER_INFO_URL,
                HttpMethod.GET,
                new HttpEntity<>(
                        new HttpHeaders() {{
                            setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                            setBearerAuth(TEST_ACCESS_TOKEN);
                        }}
                ),
                OauthUserInfo.class
        );
    }

    private ResponseEntity<OauthAccessToken> getRequestAccessToken() {
        return restTemplate.exchange(
                TEST_TOKEN_URL,
                HttpMethod.POST,
                setRequestAccessTokenHeaderAndBody(TEST_CODE),
                OauthAccessToken.class
        );
    }

    @BeforeEach
    void setUp() {
        kakaoOauthProvider = new KakaoOauthProvider(TEST_CLIENT_ID, TEST_REDIRECT_URL, TEST_TOKEN_URL, TEST_USER_INFO_URL);
    }

    @Test
    @DisplayName("카카오 로그인 서비스인지 확인한다.")
    void isKakaoProvider() {
        assertThat(kakaoOauthProvider.is("kakao")).isTrue();
        assertThat(kakaoOauthProvider.is("naver")).isFalse();
    }


    @Test
    @DisplayName("인가 코드로 AccessToken 요청 후 AccessToken을 헤더에 담아 카카오에 유저 정보를 요청한다.")
    void getUserInfo_success() {
//        // given
//        ResponseEntity<OauthAccessToken> mockResponseEntity = mock(ResponseEntity.class);
//        ResponseEntity<OauthUserInfo> mockUserInfoResponseEntity = mock(ResponseEntity.class);
//
//        given(getRequestAccessToken()).willReturn(mockResponseEntity);
//
//        given(mockResponseEntity.getBody()).willReturn(mockOauthAccessToken);
//        given(mockOauthAccessToken.getAccessToken()).willReturn(TEST_ACCESS_TOKEN);
//
//        given(getUserInfo()).willReturn(mockUserInfoResponseEntity);
//
//        // when
//        OauthUserInfo kakaoUserInfo = kakaoOauthProvider.getUserInfo(TEST_CODE);
//
//        // given
//        assertThat(kakaoUserInfo).isNotNull();
    }

}