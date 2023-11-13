package com.pd.gilgeorigoreuda.review.service;

import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.review.domain.entity.ReviewImage;
import com.pd.gilgeorigoreuda.review.dto.request.ReviewCreateRequest;
import com.pd.gilgeorigoreuda.review.dto.request.ReviewUpdateRequest;
import com.pd.gilgeorigoreuda.review.dto.response.ReviewCreateResponse;
import com.pd.gilgeorigoreuda.review.dto.response.ReviewListResponse;
import com.pd.gilgeorigoreuda.review.repository.ReviewImageRepository;
import com.pd.gilgeorigoreuda.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class  ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;

    @Transactional
    public ReviewCreateResponse createReview(final Long storeId,
                                             final Long memberId,
                                             final ReviewCreateRequest reviewCreateRequest) {

        Review review = reviewCreateRequest.toEntity(memberId, storeId);

        List<ReviewImage> reviewImages = makeImages(reviewCreateRequest);

        review.addImages(reviewImages);
        Review savedReview = reviewRepository.save(review);

        return ReviewCreateResponse.of(savedReview.getId());
    }

    private static List<ReviewImage> makeImages(final ReviewCreateRequest reviewCreateRequest) {
        return reviewCreateRequest.getImageUrls()
                .stream()
                .map(ReviewImage::new)
                .toList();
    }

    public void updateReview(final Long reviewId, final Long memberId, final ReviewUpdateRequest reviewRequest) {
        Review review = getReview(reviewId);

        review.checkAuthor(memberId);

        review.updateContent(reviewRequest.getContent());
        review.updateReviewRating(review.getReviewRating());

        reviewRepository.save(review);
    }

    public void deleteReview(final Long reviewId, final Long memberId) {
        Review review = getReview(reviewId);

        review.checkAuthor(memberId);

        reviewRepository.deleteById(reviewId);
    }

    private Review getReview(final Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public ReviewListResponse findReviewsByStoreId(Long storeId, Pageable pageable) {
        Page<Review> reviewPage = reviewRepository.findAllByStoreIdWithImages(storeId, pageable);
        return ReviewListResponse.of(reviewPage);
    }

}
