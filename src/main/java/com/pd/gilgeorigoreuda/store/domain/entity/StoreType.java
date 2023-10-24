package com.pd.gilgeorigoreuda.store.domain.entity;

import java.util.Arrays;

import com.pd.gilgeorigoreuda.store.exception.NoSuchStoreTypeException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoreType {

	FOOD_TRUCK("FOODTRUCK"),
	FOOD_STALL("FOODSTALL"),
	;

	private final String storeTypeName;

	private static StoreType of(String storeType) {
		return Arrays.stream(values())
			.filter(st -> st.getStoreTypeName().equals(storeType.toUpperCase()))
			.findFirst()
			.orElseThrow(NoSuchStoreTypeException::new);
	}

}
