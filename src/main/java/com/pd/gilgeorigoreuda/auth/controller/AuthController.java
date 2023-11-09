package com.pd.gilgeorigoreuda.auth.controller;

import com.pd.gilgeorigoreuda.auth.dto.response.LoginResponse;
import com.pd.gilgeorigoreuda.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestParam("code") String code) {
        HashMap<String, String> kakaoToken = authService.getAccessToken(code);
        LoginResponse response = authService.getUserInfo(kakaoToken.get("accessToken"));
        return ResponseEntity.ok().body(response);
    }

}
