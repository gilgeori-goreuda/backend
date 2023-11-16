package com.pd.gilgeorigoreuda.login.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequest {

    private String code;

    public LoginRequest(final String code) {
        this.code = code;
    }

}
