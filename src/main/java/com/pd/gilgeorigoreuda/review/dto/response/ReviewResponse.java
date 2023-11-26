package com.pd.gilgeorigoreuda.review.dto.response;

import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.review.domain.entity.ReviewImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewResponse {

    private Long reviewId;
    private String content;
    private Integer reviewRating;
    private Integer likeCount;
    private Integer hateCount;
    private ReviewMemberResponse member;
    private Long storeId;
    private List<String> imageUrls;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ReviewResponse(
            final Long reviewId,
            final String content,
            final Integer reviewRating,
            final Integer likeCount,
            final Integer hateCount,
            final ReviewMemberResponse member,
            final Long storeId,
            final List<String> imageUrls,
            final LocalDateTime createdAt,
            final LocalDateTime modifiedAt) {
        this.reviewId = reviewId;
        this.content = content;
        this.reviewRating = reviewRating;
        this.likeCount = likeCount;
        this.hateCount = hateCount;
        this.member = member;
        this.storeId = storeId;
        this.imageUrls = imageUrls;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ReviewResponse of(final Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getContent(),
                review.getReviewRating(),
                review.getLikeCount(),
                review.getHateCount(),
                ReviewMemberResponse.of(review.getMember()),
                review.getStore().getId(),
                review.getImages()
                        .stream()
                        .map(ReviewImage::getImageUrl)
                        .toList(),
                review.getCreatedAt(),
                review.getModifiedAt()
        );
    }
}
