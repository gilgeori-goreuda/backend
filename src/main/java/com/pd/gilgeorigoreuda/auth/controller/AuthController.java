package com.pd.gilgeorigoreuda.auth.controller;

import com.pd.gilgeorigoreuda.auth.dto.request.SignUpRequest;
import com.pd.gilgeorigoreuda.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestPart SignUpRequest request,
                                 @RequestPart List<MultipartFile> profileImage) {

        return ResponseEntity.created(URI.create("/api/v1/auth/singUp")).body(authService.signUp(request, profileImage));

    }
}
