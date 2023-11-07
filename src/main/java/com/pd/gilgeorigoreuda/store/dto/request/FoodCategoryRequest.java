package com.pd.gilgeorigoreuda.store.dto.request;

import java.util.List;

import com.pd.gilgeorigoreuda.store.domain.entity.FoodCategory;
import com.pd.gilgeorigoreuda.store.domain.entity.FoodType;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FoodCategoryRequest {

	@NotEmpty(message = "장소의 카테고리를 1개 이상 선택해주세요.")
	private List<String> foodCategories;

	public FoodCategoryRequest(final List<String> foodCategories) {
		this.foodCategories = foodCategories;
	}

	public List<FoodCategory> toEntities() {
		return foodCategories.stream()
			.map(foodCategory -> FoodCategory.builder()
				.foodType(FoodType.of(foodCategory))
				.build())
			.toList();
	}

}
