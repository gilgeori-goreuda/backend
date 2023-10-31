package com.pd.gilgeorigoreuda.home.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class HotPlaceListResponse {
    private List<HotPlaceResponse> recommendHotPlace;

    private HotPlaceListResponse(List<HotPlaceResponse> recommendHotPlace) {
        this.recommendHotPlace = recommendHotPlace;
    }

    public static HotPlaceListResponse of(List<HotPlaceResponse> recommendHotPlace) {
        return new HotPlaceListResponse(recommendHotPlace);
    }
}
