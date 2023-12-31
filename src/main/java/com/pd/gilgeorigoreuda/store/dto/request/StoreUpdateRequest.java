package com.pd.gilgeorigoreuda.store.dto.request;

import java.math.BigDecimal;
import java.time.LocalTime;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreUpdateRequest {

	@NotBlank(message = "가게 이름을 입력해주세요.")
	@Size(min = 1, max = 50, message = "가게 이름은 1에서 50자 사이여야 합니다.")
	private String name;

	@NotBlank(message = "가게 타입을 선택해주세요.")
	private String storeType;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime openTime;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime closeTime;

	@NotBlank(message = "결제방식을 선택해주세요.")
	private String purchaseType;

	@URL(message = "유효한 URL을 입력해주세요.", regexp = "^(http|https)://(www\\.)?.*")
	private String imageUrl;

	private String businessDates;

	@NotNull(message = "위도를 입력해주세요.")
	@Positive(message = "음수 값은 허용되지 않습니다.")
	@Digits(integer = 3, fraction = 38)
	private BigDecimal lat;

	@NotNull(message = "경도를 입력해주세요.")
	@Positive(message = "음수 값은 허용되지 않습니다.")
	@Digits(integer = 3, fraction = 38)
	private BigDecimal lng;

	@NotBlank(message = "도로명 주소를 입력해주세요.")
	private String streetAddress;

	private FoodCategoryRequest foodCategories;

	public StoreUpdateRequest(
			final String name,
			final String storeType,
			final LocalTime openTime,
			final LocalTime closeTime,
			final String purchaseType,
			final String imageUrl,
			final String businessDates,
			final BigDecimal lat,
			final BigDecimal lng,
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

}
