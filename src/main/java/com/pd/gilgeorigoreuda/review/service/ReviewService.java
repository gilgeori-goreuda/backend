package com.pd.gilgeorigoreuda.review.service;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.review.domain.entity.ReviewImage;
import com.pd.gilgeorigoreuda.review.dto.request.ReviewCreateRequest;
import com.pd.gilgeorigoreuda.review.dto.request.ReviewUpdateRequest;
import com.pd.gilgeorigoreuda.review.repository.ImageRepository;
import com.pd.gilgeorigoreuda.review.repository.ReviewRepository;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;

    public void createReview(Long storeId, Long memberId, ReviewCreateRequest request) {
        Review review = Review.builder()
                .content(request.getContent())
                .store(Store.builder().id(storeId).build())
                .member(Member.builder().id(memberId).build())
                .build();

        Review savedReview = reviewRepository.save(review);

        List<ReviewImage> reviewImages = request.getImageUrls()
                .stream()
                .map(
                        image -> ReviewImage.builder()
                                .imageUrl(image)
                                .review(Review.builder().id(savedReview.getId()).build())
                                .build()
                )
                .toList();

        imageRepository.saveAll(reviewImages);
    }

    public void updateReview(Long reviewId, Long memberId, ReviewUpdateRequest reviewRequest) {
        Review review = getReview(reviewId);

        // 리뷰의 작성자와 현재 사용자가 일치하는지 확인
        review.checkAuthor(memberId);

        // 기존 리뷰 내용 업데이트
        review.updateContent(reviewRequest.getContent());
        review.updateReviewRating(review.getReviewRating());
        
        reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId, Long memberId) {
        Review review = getReview(reviewId);

        // 리뷰의 작성자와 현재 사용자가 일치하는지 확인
        review.checkAuthor(memberId);

        reviewRepository.deleteById(reviewId);
    }

    private Review getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        return review;
    }
}
