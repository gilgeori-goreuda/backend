package com.pd.gilgeorigoreuda.review.service;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.review.domain.entity.ReviewImage;
import com.pd.gilgeorigoreuda.review.dto.request.ReviewRequest;
import com.pd.gilgeorigoreuda.review.repository.ImageRepository;
import com.pd.gilgeorigoreuda.review.repository.ReviewRepository;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;

    public void createReview(Long storeId, Long memberId, ReviewRequest request) {
        Review review = Review.builder()
                .content(request.getContent())
                .reviewRating(request.getReviewRating())
                .likeCount(request.getLikeCount())
                .store(Store.builder().id(storeId).build())
                .member(Member.builder().id(memberId).build())
                .images(request.getImageUrls().stream()
                        .map(image -> ReviewImage.builder().imageUrl(image).build()
                        ).toList())
                .build();

        for (String imageUrl : request.getImageUrls()) {
            ReviewImage image = ReviewImage.builder()
                    .imageUrl(imageUrl)
                    .build();
            ReviewImage reviewImage = image;
            imageRepository.save(reviewImage);
        }
        reviewRepository.save(review);
    }

    public void updateReview(Long reviewId, Long memberId, ReviewRequest reviewRequest) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        // 리뷰의 작성자와 현재 사용자가 일치하는지 확인
        if (!review.getMember().getId().equals(memberId)) {
            throw new RuntimeException("Mismatched Review");
        }
        // 기존 리뷰 내용 업데이트
        review.updateContent(reviewRequest.getContent());
        review.updateReviewRating(review.getReviewRating());
        
        reviewRepository.save(review);

    }
}
