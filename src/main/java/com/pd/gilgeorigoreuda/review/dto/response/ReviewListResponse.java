package com.pd.gilgeorigoreuda.review.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import com.pd.gilgeorigoreuda.common.page.PageInfo;
import com.pd.gilgeorigoreuda.review.domain.entity.Review;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewListResponse {

	private List<ReviewResponse> reviews;
	private PageInfo pageInfo;

	private ReviewListResponse(
			final List<ReviewResponse> reviews,
			final PageInfo pageInfo) {
		this.reviews = reviews;
		this.pageInfo = pageInfo;
	}

	public static ReviewListResponse of(final Page<Review> reviewPage) {
		return new ReviewListResponse(
				reviewPage.getContent()
					.stream()
					.map(ReviewResponse::of)
					.toList(),
				PageInfo.of(reviewPage)
		);
	}

}
