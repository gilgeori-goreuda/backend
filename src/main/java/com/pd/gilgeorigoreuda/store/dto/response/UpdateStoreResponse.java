package com.pd.gilgeorigoreuda.store.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateStoreResponse {

	private String name;
	private String storeType;
	private String businessDates;
	private String openTime;
	private String closeTime;
	private String purchaseType;
	private String imageUrl;
	private Double lat;
	private Double lng;
	private String streetAddress;
	private String lastModifiedMemberNickname;

	public UpdateStoreResponse(
			final String name,
			final String storeType,
			final String businessDates,
			final String openTime,
			final String closeTime,
			final String purchaseType,
			final String imageUrl,
			final Double lat,
			final Double lng,
			final String streetAddress,
			final String lastModifiedMemberNickname) {
		this.name = name;
		this.storeType = storeType;
		this.businessDates = businessDates;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.purchaseType = purchaseType;
		this.imageUrl = imageUrl;
		this.lat = lat;
		this.lng = lng;
		this.streetAddress = streetAddress;
		this.lastModifiedMemberNickname = lastModifiedMemberNickname;
	}

}
