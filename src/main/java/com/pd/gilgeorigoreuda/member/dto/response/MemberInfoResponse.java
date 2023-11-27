package com.pd.gilgeorigoreuda.member.dto.response;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberInfoResponse {

    private Long id;
    private String nickname;
    private String profileImageUrl;

    private MemberActiveInfoResponse memberActiveInfo;

    private MemberInfoResponse(
            final Long id,
            final String nickname,
            final String profileImageUrl,
            final MemberActiveInfoResponse memberActiveInfo
    ) {
        this.id = id;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.memberActiveInfo = memberActiveInfo;
    }

    public static MemberInfoResponse of(final Member member) {
        return new MemberInfoResponse(
                member.getId(),
                member.getNickname(),
                member.getProfileImageUrl(),
                MemberActiveInfoResponse.of(member.getMemberActiveInfo())

        );
    }

}
