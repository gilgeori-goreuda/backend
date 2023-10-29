package com.pd.gilgeorigoreuda.store.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreReviewListResponse {

	private List<StoreReviewResponse> storeReviewResponses;

	public StoreReviewListResponse(List<StoreReviewResponse> storeReviewResponses) {
		this.storeReviewResponses = storeReviewResponses;
	}

}
