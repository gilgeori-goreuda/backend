package com.pd.gilgeorigoreuda.settings.fixtures;

import com.pd.gilgeorigoreuda.store.domain.entity.FoodCategory;
import com.pd.gilgeorigoreuda.store.domain.entity.FoodType;

import static com.pd.gilgeorigoreuda.settings.fixtures.StoreFixtures.*;

public class FoodCategoryFixtures {

    public static FoodCategory BUNGEOPPANG_CATEGORY() {
        return FoodCategory.builder()
                .id(1L)
                .foodType(FoodType.of("붕어빵").getFoodName())
                .build();
    }

    public static FoodCategory EGGBREAD_CATEGORY() {
        return FoodCategory.builder()
                .id(2L)
                .foodType(FoodType.of("계란빵").getFoodName())
                .build();
    }

    public static FoodCategory ODENG_CATEGORY() {
        return FoodCategory.builder()
                .id(3L)
                .foodType(FoodType.of("오뎅").getFoodName())
                .build();
    }

    public static FoodCategory TTEOKBOKKI_CATEGORY() {
        return FoodCategory.builder()
                .id(4L)
                .foodType(FoodType.of("떡볶이").getFoodName())
                .build();
    }

    public static FoodCategory TACOYAKI_CATEGORY() {
        return FoodCategory.builder()
                .id(5L)
                .foodType(FoodType.of("타코야끼").getFoodName())
                .build();
    }

    public static FoodCategory TANGHURU_CATEGORY() {
        return FoodCategory.builder()
                .id(6L)
                .foodType(FoodType.of("탕후루").getFoodName())
                .build();
    }

}
