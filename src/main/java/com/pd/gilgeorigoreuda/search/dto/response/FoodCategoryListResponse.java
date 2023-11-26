package com.pd.gilgeorigoreuda.search.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FoodCategoryListResponse {

    private List<FoodCategoryResponse> categories;

    private FoodCategoryListResponse(final List<FoodCategoryResponse> foodCategoryResponses) {
        this.categories = foodCategoryResponses;
    }

    public static FoodCategoryListResponse of(final List<FoodCategoryResponse> foodCategoryResponses){
        return new FoodCategoryListResponse(foodCategoryResponses);
    }

}
