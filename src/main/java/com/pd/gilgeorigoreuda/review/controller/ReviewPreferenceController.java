package com.pd.gilgeorigoreuda.review.controller;

import com.pd.gilgeorigoreuda.review.service.ReviewPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/preferences")
public class ReviewPreferenceController {

    private static final Long TEST_USER_ID = 2L;

    private final ReviewPreferenceService reviewPreferenceService;
    @PostMapping("/reviews/{reviewId}/like")
    public ResponseEntity<Void> addReviewLike(@PathVariable Long reviewId) {
        reviewPreferenceService.addReviewLike(reviewId, TEST_USER_ID);

        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("/reviews/{reviewId}/hate")
    public ResponseEntity<Void> addReviewHate(@PathVariable Long reviewId) {
        reviewPreferenceService.addReviewHate(reviewId, TEST_USER_ID);

        return ResponseEntity
                .ok()
                .build();
    }
}
