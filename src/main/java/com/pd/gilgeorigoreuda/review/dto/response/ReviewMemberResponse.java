package com.pd.gilgeorigoreuda.review.dto.response;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewMemberResponse {

    private Long memberId;
    private String nickname;
    private String profileImageUrl;

    public ReviewMemberResponse(
            final Long memberId,
            final String nickname,
            final String profileImageUrl) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public static ReviewMemberResponse of(final Member member) {
        return new ReviewMemberResponse(
                member.getId(),
                member.getNickname(),
                member.getProfileImageUrl()
        );
    }

}
