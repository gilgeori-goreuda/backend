package com.pd.gilgeorigoreuda.store.dto.request;

import java.time.LocalTime;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.store.domain.entity.PurchaseType;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreType;
import com.pd.gilgeorigoreuda.store.domain.entity.StreetAddress;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreCreateRequest {

	// todo: validateMessage 분리
	@NotBlank(message = "가게 이름을 입력해주세요.")
	@Size(min = 1, max = 50, message = "가게 이름은 1에서 50자 사이여야 합니다.")
	private String name;

	@NotBlank(message = "가게 타입을 선택해주세요.")
	private String storeType;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime openTime;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime closeTime;

	@NotBlank(message = "결제방식을 선택해주세요.")
	private String purchaseType;

	@URL(message = "유효한 URL을 입력해주세요.")
	private String imageUrl;

	private String businessDates;

	@NotBlank(message = "위도를 입력해주세요.")
	@Pattern(regexp = "^-?\\d{1,2}\\.\\d{1,6}$", message = "위도는 소수점 6자리까지 입력 가능합니다.")
	private Double lat;

	@NotBlank(message = "경도를 입력해주세요.")
	@Pattern(regexp = "^-?\\d{1,3}\\.\\d{1,6}$", message = "경도는 소수점 6자리까지 입력 가능합니다.")
	private Double lng;

	@NotBlank(message = "도로명 주소를 입력해주세요.")
	private String streetAddress;

	private FoodCategoryRequest foodCategories;

	public StoreCreateRequest(
			final String name,
			final String storeType,
			final LocalTime openTime,
			final LocalTime closeTime,
			final String purchaseType,
			final String imageUrl,
			final String businessDates,
			final Double lat,
			final Double lng,
			final String streetAddress,
			final FoodCategoryRequest foodCategories) {
		this.name = name;
		this.storeType = storeType;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.purchaseType = purchaseType;
		this.imageUrl = imageUrl;
		this.businessDates = businessDates;
		this.lat = lat;
		this.lng = lng;
		this.streetAddress = streetAddress;
		this.foodCategories = foodCategories;
	}

	public Store toEntity(Long memberId) {
		return Store.builder()
			.name(name)
			.storeType(StoreType.of(storeType))
			.openTime(openTime)
			.closeTime(closeTime)
			.purchaseType(PurchaseType.of(purchaseType))
			.imageUrl(imageUrl)
			.businessDate(BusinessDateRequest.of(businessDates).toString())
			.lat(lat)
			.lng(lng)
			.streetAddress(StreetAddress.of(streetAddress))
			.member(
				Member.builder()
					.id(memberId)
					.build()
			)
			.build();
	}

}