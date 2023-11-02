package com.pd.gilgeorigoreuda.store.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreCreateResponse {

	private Long id;

	private StoreCreateResponse(final Long id) {
		this.id = id;
	}

	public static StoreCreateResponse of(final Long id) {
		return new StoreCreateResponse(id);
	}

}
