package com.pd.gilgeorigoreuda.store.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreLocationResponse {

	private Double lat;
	private Double lng;
	private String storeAddress;
	private String detailLocation;

	public StoreLocationResponse(
			final Double lat,
			final Double lng,
			final String storeAddress,
			final String detailLocation) {
		this.lat = lat;
		this.lng = lng;
		this.storeAddress = storeAddress;
		this.detailLocation = detailLocation;
	}

}
