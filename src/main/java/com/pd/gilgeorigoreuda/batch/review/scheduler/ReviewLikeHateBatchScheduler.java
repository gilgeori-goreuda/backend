package com.pd.gilgeorigoreuda.batch.review.scheduler;

import com.pd.gilgeorigoreuda.batch.review.job.ReviewLikeHateJob;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewLikeHateBatchScheduler {

    private final ReviewLikeHateJob reviewLikeHateJob;

    @Scheduled(cron ="0 0 */2 * * ?")
    public void runAllReviewLikeHateUpdate() {
        reviewLikeHateJob.run();
    }

}
