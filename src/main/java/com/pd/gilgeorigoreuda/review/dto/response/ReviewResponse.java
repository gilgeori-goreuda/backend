package com.pd.gilgeorigoreuda.review.dto.response;

import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.review.domain.entity.ReviewImage;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReviewResponse {
    private Long reviewId;
    private String content;
    private Double reviewRating;
    private Integer likeCount;
    private Long userId;
    private Long storeId;
    private List<String> imageUrls;
    private LocalDateTime createdAt;

//    public ReviewResponse(Long reviewId, String content, Double reviewRating, Integer likeCount, Long userId, Long storeId
//            , List<ReviewImage> imageUrls, LocalDateTime createdAt) {
//        this.reviewId = reviewId;
//        this.content = content;
//        this.reviewRating = reviewRating;
//        this.likeCount = likeCount;
//        this.userId = userId;
//        this.storeId = storeId;
//        this.imageUrls = imageUrls.stream().map(ReviewImage::getImageUrl).toList();
//        this.createdAt = createdAt;
//    }

    public ReviewResponse(Review review) {
        this.reviewId = review.getId();
        this.content = review.getContent();
        this.reviewRating = review.getReviewRating();
        this.likeCount = review.getLikeCount();
        this.userId = review.getUser().getId();
        this.storeId = review.getStore().getId();
        this.imageUrls = review.getImages().stream().map(ReviewImage::getImageUrl).toList();
        this.createdAt = review.getCreatedAt();
    }
}
