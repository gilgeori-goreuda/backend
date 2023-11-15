package com.pd.gilgeorigoreuda.login.domain.oauthuserinfo;

import com.pd.gilgeorigoreuda.login.domain.OauthUserInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class KakaoUserInfo implements OauthUserInfo {

    private String socialLoginId;
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
        private String nickname;
        private String profileImage;
    }

}
