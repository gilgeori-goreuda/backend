package com.pd.gilgeorigoreuda.store.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreLocationRequest {

	@NotBlank(message = "위도를 입력해주세요.")
	@Pattern(regexp = "^-?\\d{1,2}\\.\\d{1,6}$", message = "위도는 소수점 6자리까지 입력 가능합니다.")
	private Double lat;

	@NotBlank(message = "경도를 입력해주세요.")
	@Pattern(regexp = "^-?\\d{1,3}\\.\\d{1,6}$", message = "경도는 소수점 6자리까지 입력 가능합니다.")
	private Double lng;

	@NotBlank(message = "가게 주소를 입력해주세요.")
	private String storeAddress;

	@NotBlank(message = "상세 주소를 입력해주세요.")
	private String detailLocation;

	public StoreLocationRequest(
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
