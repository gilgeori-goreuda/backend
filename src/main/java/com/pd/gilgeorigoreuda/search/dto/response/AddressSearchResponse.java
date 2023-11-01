package com.pd.gilgeorigoreuda.search.dto.response;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreType;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Double lat;
    private Double lng;
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
        this.streetAddress = store.getStreetAddress();
        this.storeCategories = FoodCategoryListResponse.of(
                store.getCategories()
                        .stream()
                        .map(FoodCategoryResponse::of)
//                        .map(foodCategory -> FoodCategoryResponse.of(foodCategory))
                        .toList()
        );
    }

}
