package com.pd.gilgeorigoreuda.home.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PlaceListResponse {

    private List<PlaceResponse> recommendPlace;

    private PlaceListResponse(List<PlaceResponse> recommendPlace) {
        this.recommendPlace = recommendPlace;
    }

    public static PlaceListResponse of(List<PlaceResponse> recommendPlace) {
        return new PlaceListResponse(recommendPlace);
    }
}
