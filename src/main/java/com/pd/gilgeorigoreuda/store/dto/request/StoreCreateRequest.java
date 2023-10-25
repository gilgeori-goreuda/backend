package com.pd.gilgeorigoreuda.store.dto.request;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreCreateRequest {

	@NotBlank(message = "가게 이름을 입력해주세요.")
	private String storeName;

	@NotBlank(message = "가게 타입을 선택해주세요.")
	private String storeType;

	private String storeNumber;

	@NotBlank(message = "가게를 간단히 소개해주세요.")
	@Max(value = 200, message = "가게 소개는 200자 이내로 작성해주세요.")
	private String introduction;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime openTime;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime closeTime;

	@NotBlank(message = "결제방식을 선택해주세요.")
	private String purchaseType;

	private String storeImageUrl;

	private BusinessDateRequest businessDates = new BusinessDateRequest();

	private StoreLocationRequest storeLocation = new StoreLocationRequest();

	public StoreCreateRequest(
			final String storeName,
			final String storeType,
			final String storeNumber,
			final String introduction,
			final LocalTime openTime,
			final LocalTime closeTime,
			final String purchaseType,
			final String storeImageUrl,
			final BusinessDateRequest businessDates,
			final StoreLocationRequest storeLocation) {
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
