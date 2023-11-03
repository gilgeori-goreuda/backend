package com.pd.gilgeorigoreuda.home.dto.response;

import java.time.LocalTime;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlaceResponse {

	private Long id;
	private String imageUrl;
	private String streetAddress;
	private String detailLocation;
	private String name;
	private LocalTime openTime;
	private LocalTime closeTime;

	private PlaceResponse(
			final Long id,
			final String imageUrl,
			final String streetAddress,
			final String detailLocation,
			final String name,
			final LocalTime openTime,
			final LocalTime closeTime) {
		this.id = id;
		this.imageUrl = imageUrl;
		this.streetAddress = streetAddress;
		this.detailLocation = detailLocation;
		this.name = name;
		this.openTime = openTime;
		this.closeTime = closeTime;
	}

	public static PlaceResponse of(final Store store) {
		return new PlaceResponse(
			store.getId(),
			store.getImageUrl(),
			store.getStreetAddress().toString(),
			store.getDetailLocation(),
			store.getName(),
			store.getOpenTime(),
			store.getCloseTime()
		);
	}

}
