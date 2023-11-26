package com.pd.gilgeorigoreuda.batch.member.job;

import com.pd.gilgeorigoreuda.batch.member.service.MemberLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberLevelJob {

    private final MemberLevelService memberLevelService;

    public void updateMemberLeverJob() {
        memberLevelService.updateMemberLevel();
    }

}
