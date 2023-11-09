package com.pd.gilgeorigoreuda.search.dto.response;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class AddressSearchResponse {

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
    private FoodCategoryListResponse storeCategories;

    public AddressSearchResponse(final Store store){
        this.id = store.getId();
        this.name = store.getName();
        this.storeType = store.getStoreType();
        this.detailLocation = store.getDetailLocation();
        this.averageRating = store.getAverageRating();
        this.businessDate = store.getBusinessDate();
        this.imageUrl = store.getImageUrl();
        this.lat = store.getLat();
        this.lng = store.getLng();
        this.streetAddress = store.getStreetAddress().toString();
        this.storeCategories = FoodCategoryListResponse.of(
                store.getFoodCategories()
                        .stream()
                        .map(FoodCategoryResponse::of)
                        .toList()
        );
    }

}
