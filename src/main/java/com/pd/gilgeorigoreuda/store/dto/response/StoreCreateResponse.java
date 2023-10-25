package com.pd.gilgeorigoreuda.store.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreCreateResponse {

	private Long id;

	public StoreCreateResponse(final Long id) {
		this.id = id;
	}

}
