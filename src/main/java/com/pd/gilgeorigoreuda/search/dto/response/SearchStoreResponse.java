package com.pd.gilgeorigoreuda.search.dto.response;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class SearchStoreResponse {

    private Long id;
    private String name;
    private StoreType storeType;
    private String detailLocation;
    private Double averageRating;
    private String businessDate;
    private String imageUrl;
    private BigDecimal lat;
    private BigDecimal lng;
    private String streetAddress;
    private Integer distanceFromStore;
    private FoodCategoryListResponse storeCategories;

    public SearchStoreResponse(
            final Long id,
            final String name,
            final StoreType storeType,
            final String detailLocation,
            final Double averageRating,
            final String businessDate,
            final String imageUrl,
            final BigDecimal lat,
            final BigDecimal lng,
            final String streetAddress,
            final Integer distanceFromStore,
            final FoodCategoryListResponse storeCategories) {
        this.id = id;
        this.name = name;
        this.storeType = storeType;
        this.detailLocation = detailLocation;
        this.averageRating = averageRating;
        this.businessDate = businessDate;
        this.imageUrl = imageUrl;
        this.lat = lat;
        this.lng = lng;
        this.streetAddress = streetAddress;
        this.distanceFromStore = distanceFromStore;
        this.storeCategories = storeCategories;
    }

    public static SearchStoreResponse of(final Store store, final Integer distanceFromStore){
        return new SearchStoreResponse(
                store.getId(),
                store.getName(),
                store.getStoreType(),
                store.getDetailLocation(),
                store.getAverageRating(),
                store.getBusinessDate(),
                store.getImageUrl(),
                store.getLat(),
                store.getLng(),
                store.getStreetAddress().toString(),
                distanceFromStore,
                FoodCategoryListResponse.of(
                        store.getFoodCategories()
                                .stream()
                                .map(FoodCategoryResponse::of)
                                .toList()
                )
        );
    }

}
