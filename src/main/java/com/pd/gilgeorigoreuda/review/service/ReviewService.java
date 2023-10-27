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

    public void createReview(Long storeId, ReviewRequest request) {
        Review review = Review.builder()
                .content(request.getContent())
                .reviewRating(request.getReviewRating())
                .likeCount(request.getLikeCount())
                .member(Member.builder().id(request.getMemberId()).build())
                .store(Store.builder().id(storeId).build())
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
}
