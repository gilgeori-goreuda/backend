package com.pd.gilgeorigoreuda.batch.review.service;

import com.pd.gilgeorigoreuda.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewBatchService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public void updateAllReviewLikeHate() {
        reviewRepository.updateAllReviewLikeCount();
        reviewRepository.updateAllReviewHateCount();
    }

}
