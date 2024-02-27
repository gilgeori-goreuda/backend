package com.pd.gilgeorigoreuda.settings.fixtures;

import com.pd.gilgeorigoreuda.login.domain.MemberToken;

import static com.pd.gilgeorigoreuda.settings.fixtures.MemberFixtures.*;

public class MemberTokenFixtures {

    public static  MemberToken KIM_TOKEN() {
        return MemberToken.builder()
                .memberId(KIM().getId())
                .accessToken("Kim AccessToken")
                .refreshToken("Lee RefreshToken")
                .build();
    }

    public static  MemberToken LEE_TOKEN() {
        return MemberToken.builder()
                .memberId(LEE().getId())
                .accessToken("Lee AccessToken")
                .refreshToken("Lee RefreshToken")
                .build();
    }

}
