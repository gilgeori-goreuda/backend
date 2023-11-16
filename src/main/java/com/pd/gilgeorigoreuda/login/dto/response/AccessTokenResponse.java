package com.pd.gilgeorigoreuda.login.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessTokenResponse {

    private String accessToken;

    private AccessTokenResponse(final String accessToken) {
        this.accessToken = accessToken;
    }

    public static AccessTokenResponse of(final String accessToken) {
        return new AccessTokenResponse(accessToken);
    }

}
