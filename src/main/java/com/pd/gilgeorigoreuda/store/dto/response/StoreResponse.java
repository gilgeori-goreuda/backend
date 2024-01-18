package com.pd.gilgeorigoreuda.store.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pd.gilgeorigoreuda.store.domain.entity.FoodCategory;
import com.pd.gilgeorigoreuda.store.domain.entity.FoodType;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
public class StoreResponse {

	private Long id;
	private String name;
	private String storeType;
	private String detailLocation;
	private Double averageRating;
	private String businessDates;

	@JsonFormat(pattern = "HH:mm")
	private LocalTime openTime;

	@JsonFormat(pattern = "HH:mm")
	private LocalTime closeTime;
	
	private String purchaseType;
	private String imageUrl;
	private BigDecimal lat;
	private BigDecimal lng;
	private String streetAddress;
	private Integer totalVisitCount;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;
	private Integer distanceFromMember;

	private List<String> foodCategories;
	private String lastModifiedMemberNickname;
	private StoreOwnerResponse owner;

	public StoreResponse(
			final Long id,
			final String name,
			final String storeType,
			final String detailLocation,
			final Double averageRating,
			final String businessDates,
			final LocalTime openTime,
			final LocalTime closeTime,
			final String purchaseType,
			final String imageUrl,
			final BigDecimal lat,
			final BigDecimal lng,
			final String streetAddress,
			final Integer totalVisitCount,
			final LocalDateTime createdAt,
			final Integer distanceFromMember,
			final List<String> foodCategories,
			final String lastModifiedMemberNickname,
			final StoreOwnerResponse owner) {
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
		this.createdAt = createdAt;
		this.distanceFromMember = distanceFromMember;
		this.foodCategories = foodCategories;
		this.lastModifiedMemberNickname = lastModifiedMemberNickname;
		this.owner = owner;
	}

	public static StoreResponse of(final Store store, final Integer distanceFromMember) {
		return new StoreResponse(
				store.getId(),
				store.getName(),
				store.getStoreType().toString(),
				store.getDetailLocation(),
				store.getAverageRating(),
				store.getBusinessDate(),
				store.getOpenTime(),
				store.getCloseTime(),
				store.getPurchaseType().toString(),
				store.getImageUrl(),
				store.getLat(),
				store.getLng(),
				store.getStreetAddress().toString(),
				store.getTotalVisitCount(),
				store.getCreatedAt(),
				distanceFromMember,
				store.getFoodCategories()
						.stream()
						.map(FoodCategory::getFoodType)
						.toList(),
				store.getLastModifiedMemberNickname(),
				StoreOwnerResponse.of(store.getMember())
		);
	}

}
