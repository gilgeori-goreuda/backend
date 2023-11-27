package com.pd.gilgeorigoreuda.member.dto.response;

import com.pd.gilgeorigoreuda.member.domain.entity.MemberActiveInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberActiveInfoResponse {

    private String memberLevel;
    private Integer totalWalkingDistance;
    private Integer totalVisitCount;
    private Integer exp;

    private MemberActiveInfoResponse(
            final String memberLevel,
            final Integer totalWalkingDistance,
            final Integer totalVisitCount,
            final Integer exp) {
        this.memberLevel = memberLevel;
        this.totalWalkingDistance = totalWalkingDistance;
        this.totalVisitCount = totalVisitCount;
        this.exp = exp;
    }

    public static MemberActiveInfoResponse of(final MemberActiveInfo memberActiveInfo) {
        return new MemberActiveInfoResponse(
                memberActiveInfo.getMemberLevel().getLevel(),
                memberActiveInfo.getTotalWalkingDistance(),
                memberActiveInfo.getTotalVisitCount(),
                memberActiveInfo.getExp()
        );
    }

}
