package com.pd.gilgeorigoreuda.store.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreReviewResponse {

	private Long reviewId;
	private String content;
	private Double reviewRating;
	private Integer likeCount;
	private Integer hateCount;
	private String memberId;
	private String memberNickname;
	private String memberProfileImageUrl;
	private String memberLevel;

	public StoreReviewResponse(
			final Long reviewId,
			final String content,
			final Double reviewRating,
			final Integer likeCount,
			final Integer hateCount,
			final String memberId,
			final String memberNickname,
			final String memberProfileImageUrl,
			final String memberLevel) {
		this.reviewId = reviewId;
		this.content = content;
		this.reviewRating = reviewRating;
		this.likeCount = likeCount;
		this.hateCount = hateCount;
		this.memberId = memberId;
		this.memberNickname = memberNickname;
		this.memberProfileImageUrl = memberProfileImageUrl;
		this.memberLevel = memberLevel;
	}

}
