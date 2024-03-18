package com.pd.gilgeorigoreuda.review.controller;

import com.pd.gilgeorigoreuda.auth.annotation.MemberInfo;
import com.pd.gilgeorigoreuda.auth.annotation.MemberOnly;
import com.pd.gilgeorigoreuda.auth.domain.LoginMember;
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

    private final ReviewPreferenceService reviewPreferenceService;

    @MemberOnly
    @PostMapping("/reviews/{reviewId}/like")
    public ResponseEntity<Void> addReviewLike(
            @PathVariable Long reviewId,
            @MemberInfo final LoginMember loginMember) {
        reviewPreferenceService.addReviewLike(reviewId, loginMember.getMemberId());

        return ResponseEntity
                .ok()
                .build();
    }

    @MemberOnly
    @PostMapping("/reviews/{reviewId}/hate")
    public ResponseEntity<Void> addReviewHate(
            @PathVariable Long reviewId,
            @MemberInfo final LoginMember loginMember) {
        reviewPreferenceService.addReviewHate(reviewId, loginMember.getMemberId());

        return ResponseEntity
                .ok()
                .build();
    }
}
