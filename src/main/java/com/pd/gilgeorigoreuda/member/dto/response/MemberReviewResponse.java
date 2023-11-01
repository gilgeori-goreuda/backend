package com.pd.gilgeorigoreuda.member.dto.response;

import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.review.domain.entity.ReviewImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberReviewResponse {

    private Long reviewId;
    private String content;
    private Double reviewRating;
    private Integer likeCount;
    private Integer hateCount;
    private List<String> imageUrl;

    private MemberReviewStoreResponse storeInfo;

    private MemberReviewResponse(
            final Long reviewId,
            final String content,
            final Double reviewRating,
            final Integer likeCount,
            final Integer hateCount,
            final List<String> imageUrl,
            final MemberReviewStoreResponse storeInfo) {
        this.reviewId = reviewId;
        this.content = content;
        this.reviewRating = reviewRating;
        this.likeCount = likeCount;
        this.hateCount = hateCount;
        this.imageUrl = imageUrl;
        this.storeInfo = storeInfo;
    }

    public static MemberReviewResponse of(Review review) {
//        List<ReviewImage> images = review.getImages();
//        List<String> imageUrls = new ArrayList<>();
//        for (ReviewImage image : images) {
//            imageUrls.add(image.getImageUrl());
//        }

        return new MemberReviewResponse(
                review.getId(),
                review.getContent(),
                review.getReviewRating(),
                review.getLikeCount(),
                review.getHateCount(),
                review.getImages()
                        .stream()
                        .map(ReviewImage::getImageUrl)
                        .toList(),
                MemberReviewStoreResponse.of(review.getStore())
        );
    }
}
