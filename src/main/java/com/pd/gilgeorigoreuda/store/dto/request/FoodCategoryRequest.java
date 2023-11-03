package com.pd.gilgeorigoreuda.store.dto.request;

import java.util.List;

import com.pd.gilgeorigoreuda.store.domain.entity.FoodCategory;
import com.pd.gilgeorigoreuda.store.domain.entity.FoodType;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FoodCategoryRequest {

	private List<@NotBlank(message = "카테고리를 하나 이상 지정해주세요.") String> foodCategories;

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
