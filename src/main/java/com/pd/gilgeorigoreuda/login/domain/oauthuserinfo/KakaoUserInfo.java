package com.pd.gilgeorigoreuda.login.domain.oauthuserinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pd.gilgeorigoreuda.login.domain.OauthUserInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class KakaoUserInfo implements OauthUserInfo {

    @JsonProperty("id")
    private String socialLoginId;

    @JsonProperty("properties")
    private Properties properties;

    @Override
    public String getSocialId() {
        return this.socialLoginId;
    }

    @Override
    public String getNickname() {
        return this.properties.nickname;
    }

    @Override
    public String getProfileImageUrl() {
        return this.properties.profileImage;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Properties {

        @JsonProperty("nickname")
        private String nickname;

        @JsonProperty("profile_image")
        private String profileImage;

    }

}
