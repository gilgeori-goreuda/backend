package com.pd.gilgeorigoreuda.login.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAccessRefreshToken {

    private String refreshToken;
    private String accessToken;

    private MemberAccessRefreshToken(final String refreshToken, final String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }

    public static MemberAccessRefreshToken of(final String refreshToken, final String accessToken) {
        return new MemberAccessRefreshToken(refreshToken, accessToken);
    }

}
