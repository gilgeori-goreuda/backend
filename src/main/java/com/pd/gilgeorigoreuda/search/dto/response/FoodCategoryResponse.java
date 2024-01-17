package com.pd.gilgeorigoreuda.search.dto.response;

import com.pd.gilgeorigoreuda.store.domain.entity.FoodCategory;
import com.pd.gilgeorigoreuda.store.domain.entity.FoodType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FoodCategoryResponse {

    private Long id;
    private String foodType;

    private FoodCategoryResponse(final Long id, final String foodType) {
        this.id = id;
        this.foodType = foodType;
    }

    public static FoodCategoryResponse of(final FoodCategory foodCategory) {
        return new FoodCategoryResponse(foodCategory.getId(), foodCategory.getFoodType());
    }

}
