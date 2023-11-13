package com.pd.gilgeorigoreuda.review.service;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.review.dto.request.ReviewCreateRequest;
import com.pd.gilgeorigoreuda.review.dto.request.ReviewUpdateRequest;
import com.pd.gilgeorigoreuda.review.dto.response.ReviewListResponse;
import com.pd.gilgeorigoreuda.review.dto.response.ReviewResponse;
import com.pd.gilgeorigoreuda.review.repository.ReviewImageRepository;
import com.pd.gilgeorigoreuda.review.repository.ReviewRepository;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class  ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;

    @Value("g-reviewimages")
    private String bucket;

    @Transactional
    public void createReview(final Long storeId,
                             final Long memberId,
                             final ReviewCreateRequest request,
                             final List<MultipartFile> files) {

        Review review = Review.builder()
                .content(request.getContent())
                .reviewRating(request.getReviewRating())
                .store(Store.builder().id(storeId).build())
                .member(Member.builder().id(memberId).build())
                .build();

        Review savedReview = reviewRepository.save(review);

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
