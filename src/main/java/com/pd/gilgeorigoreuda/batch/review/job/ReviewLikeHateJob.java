package com.pd.gilgeorigoreuda.batch.review.job;

import com.pd.gilgeorigoreuda.batch.review.service.ReviewBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewLikeHateJob {

    private final ReviewBatchService reviewBatchService;

    public void run() {
        reviewBatchService.updateAllReviewLikeHate();
    }

}
