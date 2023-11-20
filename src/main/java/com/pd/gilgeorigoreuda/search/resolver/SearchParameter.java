package com.pd.gilgeorigoreuda.search.resolver;

import com.pd.gilgeorigoreuda.store.domain.entity.FoodType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchParameter {

    private BigDecimal mLat;
    private BigDecimal mLng;
    private BigDecimal rLat;
    private BigDecimal rLng;
    private FoodType foodType;

    public static SearchParameter of(
            final String mLat,
            final String mLng,
            final String rLat,
            final String rLng,
            final String foodType
    ) {
        return new SearchParameter(
                new BigDecimal(mLat),
                new BigDecimal(mLng),
                new BigDecimal(rLat),
                new BigDecimal(rLng),
                initFoodType(foodType)
        );
    }

    private static FoodType initFoodType(final String foodType) {
        return foodType == null ? null : FoodType.of(foodType);
    }

}
