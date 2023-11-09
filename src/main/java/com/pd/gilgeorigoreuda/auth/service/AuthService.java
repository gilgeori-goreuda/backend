package com.pd.gilgeorigoreuda.auth.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pd.gilgeorigoreuda.auth.dto.response.LoginResponse;
import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberRepository memberRepository;

    @Transactional
    public HashMap<String, String> getAccessToken(String code) {
        HashMap<String, String> kakaoToken = new HashMap<>();

        try {
            String reqURL = "https://kauth.kakao.com/oauth/token";
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            String sb = "grant_type=authorization_code" +
                    "&client_id=92c750c4838ccb15f3d0313f33ec5dde" +
                    "&redirect_uri=http://localhost:3000/oauth/kakao/callback" +
                    "&code=" + code;
            bw.write(sb);
            bw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            JsonElement element = JsonParser.parseString(result.toString());

            kakaoToken.put("accessToken", element.getAsJsonObject().get("access_token").getAsString());
            kakaoToken.put("refreshToken", element.getAsJsonObject().get("refresh_token").getAsString());

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return kakaoToken;
    }

    @Transactional
    public LoginResponse getUserInfo(String access_token) {
        HashMap<String, String> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Bearer " + access_token);
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            JsonElement element = JsonParser.parseString(result.toString());
            String id = element.getAsJsonObject().get("id").getAsString();
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String profileImage = properties.getAsJsonObject().get("profile_image").getAsString();
            userInfo.put("nickname", nickname);
            userInfo.put("providerId", id);
            userInfo.put("profileImage", profileImage);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Optional<Member> providerId = memberRepository.findByProviderId(userInfo.get("providerId"));

        if (providerId.isPresent()) {
            return LoginResponse.of(providerId.orElseThrow());
        }
        else {
            Member save = memberRepository.save(Member
                    .builder()
                    .providerId(userInfo.get("providerId"))
                    .nickname(userInfo.get("nickname"))
                    .profileImageUrl(userInfo.get("profileImage"))
                    .build());
            return LoginResponse.of(save);
        }
    }

}
