package com.pd.gilgeorigoreuda.review.dto.response;

import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.review.domain.entity.ReviewImage;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
public class ReviewResponse {

    private Long reviewId;
    private String content;
    private Integer reviewRating;
    private Integer likeCount;
    private Integer hateCount;
    private Long memberId;
    private Long storeId;
    private List<String> imageUrls;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ReviewResponse(Review review) {
        this.reviewId = review.getId();
        this.content = review.getContent();
        this.reviewRating = review.getReviewRating();
        this.likeCount = review.getLikeCount();
        this.hateCount = review.getHateCount();
        this.memberId = review.getMember().getId();
        this.storeId = review.getStore().getId();
        this.imageUrls = review.getImages().stream().map(ReviewImage::getImageUrl).toList();
        this.createdAt = review.getCreatedAt();
        this.modifiedAt = review.getModifiedAt();
    }

}
