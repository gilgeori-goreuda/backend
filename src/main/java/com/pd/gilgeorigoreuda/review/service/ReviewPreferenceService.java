package com.pd.gilgeorigoreuda.review.service;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.review.domain.entity.ReviewPreference;
import com.pd.gilgeorigoreuda.review.domain.entity.ReviewPreferenceType;
import com.pd.gilgeorigoreuda.review.repository.ReviewPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewPreferenceService {

    private final ReviewPreferenceRepository reviewPreferenceRepository;

    private void changePreferenceStatus(Optional<ReviewPreference> reviewPreference, ReviewPreferenceType type, Long reviewId, Long memberId) {
        reviewPreference.ifPresentOrElse(
                existingReviewPreference -> {
                    if (existingReviewPreference.getPreferenceType().equals(type)) {
                        existingReviewPreference.changePreference(ReviewPreferenceType.NONE);
                    } else {
                        existingReviewPreference.changePreference(type);
                    }
                },
                () -> {
                    reviewPreferenceRepository.save(
                            ReviewPreference.builder()
                                    .review(Review.builder().id(reviewId).build())
                                    .member(Member.builder().id(memberId).build())
                                    .preferenceType(type)
                                    .build()
                    );
                }
        );
    }

    public void addReviewLike(Long reviewId, Long memberId) {
        Optional<ReviewPreference> reviewPreference = reviewPreferenceRepository.findByReviewIdAndMemberId(reviewId, memberId);

        changePreferenceStatus(reviewPreference, ReviewPreferenceType.LIKE, reviewId, memberId);
    }

    public void addReviewHate(Long reviewId, Long memberId) {
        Optional<ReviewPreference> reviewPreference = reviewPreferenceRepository.findByReviewIdAndMemberId(reviewId, memberId);

        changePreferenceStatus(reviewPreference, ReviewPreferenceType.HATE, reviewId, memberId);
    }
}
