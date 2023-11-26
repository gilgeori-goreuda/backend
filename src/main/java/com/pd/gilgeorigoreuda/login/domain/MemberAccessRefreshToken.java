package com.pd.gilgeorigoreuda.login.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAccessRefreshToken {

    private String accessToken;
    private String refreshToken;

    private MemberAccessRefreshToken(final String accessToken, final String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static MemberAccessRefreshToken of(final String accessToken, final String refreshToken) {
        return new MemberAccessRefreshToken(accessToken, refreshToken);
    }

}
