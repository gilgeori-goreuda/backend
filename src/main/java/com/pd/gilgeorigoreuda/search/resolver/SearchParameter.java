package com.pd.gilgeorigoreuda.search.resolver;

import com.pd.gilgeorigoreuda.store.domain.entity.FoodType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

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
                initLatLng(mLat),
                initLatLng(mLng),
                initLatLng(rLat),
                initLatLng(rLng),
                initFoodType(foodType)
        );
    }

    private static BigDecimal initLatLng(final String latLng) {
        if (latLng == null || latLng.isBlank()) {
            return BigDecimal.ZERO;
        } else {
            return new BigDecimal(latLng);
        }
    }

    private static FoodType initFoodType(final String foodType) {
        return Objects.equals(foodType, "") ? null : FoodType.of(foodType);
    }

}
