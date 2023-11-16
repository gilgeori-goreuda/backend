package com.pd.gilgeorigoreuda.login.controller;

import com.pd.gilgeorigoreuda.login.dto.request.LoginRequest;
import com.pd.gilgeorigoreuda.login.dto.response.AccessTokenResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class LoginTestController {

    @GetMapping("/oauth2/callback/kakao")
    public void test(
            @RequestParam("code") String code
    ) {
        System.out.println("code : " + code);
        LoginRequest loginRequest = new LoginRequest(code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(loginRequest, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AccessTokenResponse> exchange = restTemplate.exchange(
                "http://localhost:8080/login/kakao",
                HttpMethod.POST,
                requestEntity,
                AccessTokenResponse.class
        );

        System.out.println("accessToken : " + exchange.getBody().getAccessToken());
    }

}
