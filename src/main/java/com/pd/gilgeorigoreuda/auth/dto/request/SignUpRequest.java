package com.pd.gilgeorigoreuda.auth.dto.request;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String name;
    private String email;
    private String nickname;

    public Member toEntity(String profileImageUrl) {
        return Member
                .builder()
                .name(name)
                .email(email)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .build();
    }
}
