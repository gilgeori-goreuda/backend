package com.pd.gilgeorigoreuda.store.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreResponse {

	private Long id;
	private String storeName;
	private String storeType;
	private String storeNumber;
	private String introduction;
	private String openTime;
	private String closeTime;
	private String purchaseType;
	private String storeImageUrl;
	private String businessDates;
	private StoreLocationResponse storeLocation;

	public StoreResponse(
			final Long id,
			final String storeName,
			final String storeType,
			final String storeNumber,
			final String introduction,
			final String openTime,
			final String closeTime,
			final String purchaseType,
			final String storeImageUrl,
			final String businessDates,
			final StoreLocationResponse storeLocation) {
		this.id = id;
		this.storeName = storeName;
		this.storeType = storeType;
		this.storeNumber = storeNumber;
		this.introduction = introduction;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.purchaseType = purchaseType;
		this.storeImageUrl = storeImageUrl;
		this.businessDates = businessDates;
		this.storeLocation = storeLocation;
	}

}
