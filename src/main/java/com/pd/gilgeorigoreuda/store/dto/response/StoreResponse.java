package com.pd.gilgeorigoreuda.store.dto.response;

import java.math.BigDecimal;
import java.util.List;

import com.pd.gilgeorigoreuda.store.domain.entity.FoodCategory;
import com.pd.gilgeorigoreuda.store.domain.entity.FoodType;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreResponse {

	private Long id;
	private String name;
	private String storeType;
	private String detailLocation;
	private Double averageRating;
	private String businessDates;
	private String openTime;
	private String closeTime;
	private String purchaseType;
	private String imageUrl;
	private BigDecimal lat;
	private BigDecimal lng;
	private String streetAddress;
	private Integer totalVisitCount;
	private String lastModifiedMemberNickname;
	private StoreOwnerResponse owner;
	private List<String> foodCategories;

	public StoreResponse(
			final Long id,
			final String name,
			final String storeType,
			final String detailLocation,
			final Double averageRating,
			final String businessDates,
			final String openTime,
			final String closeTime,
			final String purchaseType,
			final String imageUrl,
			final BigDecimal lat,
			final BigDecimal lng,
			final String streetAddress,
			final Integer totalVisitCount,
			final String lastModifiedMemberNickname,
			final StoreOwnerResponse owner,
			final List<String> foodCategories) {
		this.id = id;
		this.name = name;
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
		this.owner = owner;
		this.foodCategories = foodCategories;
	}

	public static StoreResponse of(Store store) {
		return new StoreResponse(
				store.getId(),
				store.getName(),
				store.getStoreType().toString(),
				store.getDetailLocation(),
				store.getAverageRating(),
				store.getBusinessDate(),
				store.getOpenTime().toString(),
				store.getCloseTime().toString(),
				store.getPurchaseType().toString(),
				store.getImageUrl(),
				store.getLat(),
				store.getLng(),
				store.getStreetAddress().toString(),
				store.getTotalVisitCount(),
				store.getLastModifiedMemberNickname(),
				StoreOwnerResponse.of(store.getMember()),
				store.getFoodCategories()
					.stream()
					.map(FoodCategory::getFoodType)
					.map(FoodType::getFoodName)
					.toList()
		);
	}

}
