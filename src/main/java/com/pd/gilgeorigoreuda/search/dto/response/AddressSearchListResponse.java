package com.pd.gilgeorigoreuda.search.dto.response;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.store.domain.entity.PurchaseType;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
//    private List<String> categories;

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
//        this.categories = store.getCategory();
    }
}
