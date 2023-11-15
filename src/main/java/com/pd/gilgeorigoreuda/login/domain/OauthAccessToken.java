package com.pd.gilgeorigoreuda.login.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OauthAccessToken {

    private String accessToken;

    private String refreshToken;

    private int expiresIn;

    private int tokenIn;

    private String scope;

}
