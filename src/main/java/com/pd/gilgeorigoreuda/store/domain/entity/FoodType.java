package com.pd.gilgeorigoreuda.store.domain.entity;

import java.util.Arrays;

import com.pd.gilgeorigoreuda.store.exception.NoSuchFoodTypeException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FoodType {

	BUNGEOPPANG("붕어빵"),
	HOTTEOK("호떡"),
	TACOYAKI("타코야끼"),
	EGGBREAD("계란빵"),
	TTEOKBOKKI("떡볶이"),
	SUNDAE("순대"),
	ODENG("오뎅"),
	WAFFLE("와플"),
	GIMBAP("김밥"),
	KKOCHI("꼬치"),
	TTAKONGPPANG("땅콩빵"),
	KUNGOGUMA("군고구마"),
	TOAST("토스트"),
	DALGONA("달고나"),
	KUNOKSUSU("군옥수수"),
	TANGHURU("탕후루"),
	FIRED("튀김")
	;

	private final String foodName;

	public static FoodType of(final String foodName) {
		return Arrays.stream(values())
			.filter(ft -> ft.getFoodName().equals(foodName))
			.findFirst()
			.orElseThrow(NoSuchFoodTypeException::new);
	}

}
