package com.pd.gilgeorigoreuda.store.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StorePreferenceType {

	PREFERRED("선호"),
	NOT_PREFERRED("비선호");

	private final String value;

}
