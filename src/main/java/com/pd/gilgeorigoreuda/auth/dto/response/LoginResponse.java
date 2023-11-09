package com.pd.gilgeorigoreuda.auth.dto.response;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.font.LayoutPath;

@Getter
@NoArgsConstructor
public class LoginResponse {
    private Long id;
    private String nickname;
    private String profileImageUrl;

    private LoginResponse(final Long id,
                          final String nickname,
                          final String profileImageUrl) {
        this.id = id;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public static LoginResponse of(Member member) {
        return new LoginResponse(
                member.getId(),
                member.getNickname(),
                member.getProfileImageUrl()
        );
    }
}
