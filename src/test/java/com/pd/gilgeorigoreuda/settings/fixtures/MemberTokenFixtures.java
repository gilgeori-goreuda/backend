package com.pd.gilgeorigoreuda.settings.fixtures;

import com.pd.gilgeorigoreuda.login.domain.MemberToken;

import static com.pd.gilgeorigoreuda.settings.fixtures.MemberFixtures.*;

public class MemberTokenFixtures {

    public static  MemberToken KIM_TOKEN() {
        return MemberToken.builder()
                .memberId(KIM().getId())
                .accessToken("AccessToken")
                .refreshToken("RefreshToken")
                .build();
    }

}
