package com.pd.gilgeorigoreuda.store.dto.response;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreReportStoreResponse {

	private Long id;
	private String name;
	private String imageUrl;

	public StoreReportStoreResponse(final Long id, String name, final String imageUrl) {
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
	}

	public static StoreReportStoreResponse of(final Store store) {
		return new StoreReportStoreResponse(
				store.getId(),
				store.getName(),
				store.getImageUrl()
		);
	}

}
