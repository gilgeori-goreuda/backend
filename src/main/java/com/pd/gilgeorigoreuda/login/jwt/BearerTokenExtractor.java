package com.pd.gilgeorigoreuda.login.jwt;

import com.pd.gilgeorigoreuda.login.exception.InvalidAccessTokenException;
import org.springframework.stereotype.Component;

@Component
public class BearerTokenExtractor {

    private static final String BEARER_TYPE = "Bearer ";

    public String extractAccessToken(String header) {
        if (header != null && header.startsWith(BEARER_TYPE)) {
            return header.substring(BEARER_TYPE.length()).trim();
        }

        throw new InvalidAccessTokenException();
    }

}