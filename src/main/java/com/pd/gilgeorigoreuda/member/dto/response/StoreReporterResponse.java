package com.pd.gilgeorigoreuda.member.dto.response;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreReporterResponse {

    private Long memberId;
    private String nickname;

    private StoreReporterResponse(final Long memberId, final String nickname) {
        this.memberId = memberId;
        this.nickname = nickname;
    }

    public static StoreReporterResponse of(final Member member) {
        return new StoreReporterResponse(member.getId(), member.getNickname());
    }

}
