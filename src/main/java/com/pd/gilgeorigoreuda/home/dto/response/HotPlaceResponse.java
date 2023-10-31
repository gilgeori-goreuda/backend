package com.pd.gilgeorigoreuda.home.dto.response;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HotPlaceResponse {
    private Long id;
    private String imageUrl;
    private String streetAddress;
    private String detailLocation;
    private String name;
    private String openTime;
    private String closeTime;

    private HotPlaceResponse(Long id,
                            String imageUrl,
                            String streetAddress,
                            String detailLocation,
                            String name,
                            String openTime,
                            String closeTime) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.streetAddress = streetAddress;
        this.detailLocation = detailLocation;
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public static HotPlaceResponse of(Store store) {
        return new HotPlaceResponse(
                store.getId(),
                store.getImageUrl(),
                store.getStreetAddress(),
                store.getDetailLocation(),
                store.getName(),
                store.getOpenTime(),
                store.getCloseTime()
        );
    }
}
