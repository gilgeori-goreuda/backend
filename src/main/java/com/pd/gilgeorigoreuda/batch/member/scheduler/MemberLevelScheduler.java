package com.pd.gilgeorigoreuda.batch.member.scheduler;

import com.pd.gilgeorigoreuda.batch.member.job.MemberLevelJob;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberLevelScheduler {

    private final MemberLevelJob memberLevelJob;

    @Scheduled(cron ="0 0 5 * * ?")
    public void runMemberLevelJob() {
        memberLevelJob.updateMemberLeverJob();
    }

}
