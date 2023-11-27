package com.pd.gilgeorigoreuda.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberInfoResponse {

    private Long id;
    private String nickname;
    private String profileImageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    private MemberActiveInfoResponse memberActiveInfo;

    private MemberInfoResponse(
            final Long id,
            final String nickname,
            final String profileImageUrl,
            final LocalDateTime createdAt,
            final MemberActiveInfoResponse memberActiveInfo
    ) {
        this.id = id;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.createdAt = createdAt;
        this.memberActiveInfo = memberActiveInfo;
    }

    public static MemberInfoResponse of(final Member member) {
        return new MemberInfoResponse(
                member.getId(),
                member.getNickname(),
                member.getProfileImageUrl(),
                member.getCreatedAt(),
                MemberActiveInfoResponse.of(member.getMemberActiveInfo())

        );
    }

}
