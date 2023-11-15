package com.pd.gilgeorigoreuda.login.controller;

import com.pd.gilgeorigoreuda.login.domain.MemberToken;
import com.pd.gilgeorigoreuda.login.dto.request.LoginRequest;
import com.pd.gilgeorigoreuda.login.dto.response.AccessTokenResponse;
import com.pd.gilgeorigoreuda.login.service.LoginService;

import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    public static final int COOKIE_AGE_SECONDS = 604800;

    private final LoginService loginService;

    @PostMapping("/login/{provider}")
    public ResponseEntity<AccessTokenResponse> login(
            @PathVariable final String provider,
            @RequestBody final LoginRequest loginRequest,
            final HttpServletResponse response
    ) {
        log.info("provider : {}", provider);
        log.info("code : {}", loginRequest.getCode());
        log.info("response : {}", response);

        MemberToken memberTokens = loginService.login(provider, loginRequest.getCode());

        ResponseCookie cookie = ResponseCookie.from("refresh-token", memberTokens.getRefreshToken())
                .maxAge(COOKIE_AGE_SECONDS)
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .path("/")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AccessTokenResponse.of(memberTokens.getAccessToken()));
    }

    @PostMapping("/token")
    public ResponseEntity<AccessTokenResponse> extendLogin(
            @CookieValue("refresh-token") final String refreshToken,
            @RequestHeader("Authorization") final String authorizationHeader
    ) {
        final String renewalRefreshToken = loginService.renewalAccessToken(refreshToken, authorizationHeader);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AccessTokenResponse.of(renewalRefreshToken));
    }

    @DeleteMapping("/logout")
    // TODO: 권한 검증
    public ResponseEntity<Void> logout(
            // TODO: 로그인 유저 정보
            @CookieValue("refresh-token") final String refreshToken
    ) {
        loginService.removeRefreshToken(refreshToken);
        return ResponseEntity.noContent().build();
    }

}
