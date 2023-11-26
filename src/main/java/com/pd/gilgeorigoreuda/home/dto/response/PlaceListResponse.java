package com.pd.gilgeorigoreuda.home.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PlaceListResponse {

    private List<PlaceResponse> recommendPlace;

    private PlaceListResponse(final List<PlaceResponse> recommendPlace) {
        this.recommendPlace = recommendPlace;
    }

    public static PlaceListResponse of(final List<PlaceResponse> recommendPlace) {
        return new PlaceListResponse(recommendPlace);
    }
}
