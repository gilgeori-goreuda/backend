package com.pd.gilgeorigoreuda.store.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreResponse {

	private Long id;
	private String storeName;
	private String storeType;
	private String detailLocation;
	private Double averageRating;
	private String businessDates;
	private String openTime;
	private String closeTime;
	private String purchaseType;
	private String imageUrl;
	private Double lat;
	private Double lng;
	private String streetAddress;
	private Integer totalVisitCount;
	private String lastModifiedMemberNickname;

	private ReporterResponse reporterInfo;

	private StoreReviewListResponse reviews;

	public StoreResponse(final Long id, final String storeName, final String storeType, final String detailLocation, final Double averageRating,
		final String businessDates, final String openTime, final String closeTime, final String purchaseType, final String imageUrl, final Double lat,
		final Double lng, final String streetAddress, final Integer totalVisitCount, final String lastModifiedMemberNickname,
		final ReporterResponse reporterInfo, final StoreReviewListResponse reviews) {
		this.id = id;
		this.storeName = storeName;
		this.storeType = storeType;
		this.detailLocation = detailLocation;
		this.averageRating = averageRating;
		this.businessDates = businessDates;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.purchaseType = purchaseType;
		this.imageUrl = imageUrl;
		this.lat = lat;
		this.lng = lng;
		this.streetAddress = streetAddress;
		this.totalVisitCount = totalVisitCount;
		this.lastModifiedMemberNickname = lastModifiedMemberNickname;
		this.reporterInfo = reporterInfo;
		this.reviews = reviews;
	}

}
