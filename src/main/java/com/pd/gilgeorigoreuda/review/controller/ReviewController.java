package com.pd.gilgeorigoreuda.review.controller;

import com.pd.gilgeorigoreuda.review.dto.request.ReviewCommentRequest;
import com.pd.gilgeorigoreuda.review.dto.request.ReviewRequest;
import com.pd.gilgeorigoreuda.review.service.ReviewCommentService;
import com.pd.gilgeorigoreuda.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewCommentService commentService;

    @PostMapping("{storeId}/{memberId}")
    public void save(@PathVariable("storeId") Long storeId,
                     @PathVariable("memberId") Long memberId,
                     @RequestBody ReviewRequest request) {
        reviewService.createReview(storeId, memberId, request);
    }

    @PutMapping("{reviewId}/{memberId}")
    public void updateReview(@PathVariable("reviewId") Long reviewId,
                             @PathVariable("memberId") Long memberId,
                             @RequestBody ReviewRequest reviewRequest) {
        reviewService.updateReview(reviewId, memberId, reviewRequest);

    }

    @DeleteMapping("{reviewId}/{memberId}")
    public void deleteReview(@PathVariable("reviewId") Long reviewId,
                             @PathVariable("memberId") Long memberId) {
        reviewService.deleteReview(reviewId, memberId);
    }

    @PostMapping("{reviewId}/comment/{memberId}")
    public void saveComment(@PathVariable("reviewId") Long reviewId,
                            @PathVariable("memberId") Long memberId,
                            @RequestBody ReviewCommentRequest commentRequest) {

        commentService.saveComment(reviewId, memberId, commentRequest);
    }
}

