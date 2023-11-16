package com.pd.gilgeorigoreuda.login.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberToken {

    private String refreshToken;
    private String accessToken;

    private MemberToken(final String refreshToken, final String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }

    public static MemberToken of(final String refreshToken, final String accessToken) {
        return new MemberToken(refreshToken, accessToken);
    }

}
