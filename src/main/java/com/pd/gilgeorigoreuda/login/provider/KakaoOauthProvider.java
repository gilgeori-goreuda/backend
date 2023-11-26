package com.pd.gilgeorigoreuda.login.provider;

import com.pd.gilgeorigoreuda.login.domain.OauthAccessToken;
import com.pd.gilgeorigoreuda.login.domain.OauthProvider;
import com.pd.gilgeorigoreuda.login.domain.OauthUserInfo;
import com.pd.gilgeorigoreuda.login.domain.oauthuserinfo.KakaoUserInfo;
import com.pd.gilgeorigoreuda.login.exception.InvalidAuthorizationCodeException;
import com.pd.gilgeorigoreuda.login.exception.NotSupportedOauthServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

@Slf4j
@Component
public class KakaoOauthProvider implements OauthProvider {

    private static final String PROVIDER_NAME = "kakao";

    private final String clientId;
    private final String redirectUrl;
    private final String tokenUrl;
    private final String userInfoUrl;

    public KakaoOauthProvider(
            @Value("${oauth2.provider.kakao.client-id}") final String clientId,
            @Value("${oauth2.provider.kakao.redirect-url}") final String redirectUrl,
            @Value("${oauth2.provider.kakao.token-url}") final String tokenUrl,
            @Value("${oauth2.provider.kakao.user-info-url}") final String userInfoUrl
    ) {
        this.clientId = clientId;
        this.redirectUrl = redirectUrl;
        this.tokenUrl = tokenUrl;
        this.userInfoUrl = userInfoUrl;
    }

    @Override
    public boolean is(final String name) {
        return PROVIDER_NAME.equals(name);
    }

    @Override
    public OauthUserInfo getUserInfo(final String code) {
        String accessToken = requestAccessToken(code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(accessToken);

        HttpEntity<MultiValueMap<String, String>> userInfoRequestEntity = new HttpEntity<>(headers);

        ResponseEntity<? extends OauthUserInfo> kakaoUserInfoResponse = getKakaoUserInfoResponseEntity(
                userInfoUrl,
                HttpMethod.GET,
                userInfoRequestEntity,
                KakaoUserInfo.class
        );

        if (kakaoUserInfoResponse.getStatusCode().is2xxSuccessful()) {
            return kakaoUserInfoResponse.getBody();
        }

        throw new NotSupportedOauthServiceException();
    }

    private String requestAccessToken(final String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUrl);
        body.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> accessTokenRequestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<OauthAccessToken> accessTokenResponse = getOauthAccessTokenResponseEntity(
                tokenUrl,
                HttpMethod.POST,
                accessTokenRequestEntity,
                OauthAccessToken.class
        );

        log.info("accessTokenResponse.getAccessToken : {}", accessTokenResponse.getBody().getAccessToken());
        log.info("accessTokenResponse.getRefreshToken : {}", accessTokenResponse.getBody().getRefreshToken());

        return Optional.ofNullable(accessTokenResponse.getBody())
                .orElseThrow(InvalidAuthorizationCodeException::new)
                .getAccessToken();
    }

    private ResponseEntity<? extends OauthUserInfo> getKakaoUserInfoResponseEntity(
            final String url,
            final HttpMethod httpMethod,
            final HttpEntity<MultiValueMap<String, String>> requestEntity,
            final Class<? extends OauthUserInfo> responseType) {
        return restTemplate.exchange(
                url,
                httpMethod,
                requestEntity,
                responseType
        );
    }

    private ResponseEntity<OauthAccessToken> getOauthAccessTokenResponseEntity(
            final String url,
            final HttpMethod httpMethod,
            final HttpEntity<MultiValueMap<String, String>> accessTokenRequestEntity,
            final Class<OauthAccessToken> responseType) {
        return restTemplate.exchange(
                url,
                httpMethod,
                accessTokenRequestEntity,
                responseType
        );
    }

}
