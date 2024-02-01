package com.pd.gilgeorigoreuda.settings.fixtures;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;

import static com.pd.gilgeorigoreuda.settings.fixtures.MemberActiveInfoFixtures.*;

public class MemberFixtures {

    public static Member KIM() {
        return Member.builder()
                .id(1L)
                .nickname("KimKim")
                .profileImageUrl("http://image.com")
                .socialId("123456789")
                .memberActiveInfo(KIM_ACTIVE_INFO())
                .build();
    }

    public static Member LEE() {
        return Member.builder()
                .id(2L)
                .nickname("LeeLee")
                .profileImageUrl("http://image2.com")
                .socialId("987654321")
                .memberActiveInfo(LEE_ACTIVE_INFO())
                .build();
    }

}
