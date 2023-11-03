package com.pd.gilgeorigoreuda.store.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class StreetAddress {

	@Column(name = "large_address", nullable = false, length = 100)
	private String largeAddress;

	@Column(name = "medium_address", nullable = false, length = 100)
	private String mediumAddress;

	@Column(name = "small_address", nullable = false, length = 100)
	private String smallAddress;

	private StreetAddress(
			final String largeAddress,
			final String mediumAddress,
			final String smallAddress) {
		this.largeAddress = largeAddress;
		this.mediumAddress = mediumAddress;
		this.smallAddress = smallAddress;
	}

	public static StreetAddress of(final String fullStreetAddress) {
		String[] streetAddressParts = splitAddress(fullStreetAddress);
		return new StreetAddress(streetAddressParts[0], streetAddressParts[1], streetAddressParts[2]);
	}

	public static String[] splitAddress(final String fullStreetAddress) {
		return fullStreetAddress.split(" ", 3);
	}

	@Override
	public String toString() {
		return largeAddress + " " + mediumAddress + " " + smallAddress;
	}

}
