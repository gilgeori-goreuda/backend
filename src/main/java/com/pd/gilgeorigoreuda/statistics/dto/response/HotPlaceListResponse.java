package com.pd.gilgeorigoreuda.statistics.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class HotPlaceListResponse {

    private List<HotPlaceResponse> hotPlaceResponses;

    private HotPlaceListResponse(final List<HotPlaceResponse> hotPlaceResponses) {
        this.hotPlaceResponses = hotPlaceResponses;
    }

    public static HotPlaceListResponse of(final List<HotPlaceResponse> hotPlaceResponses) {
        return new HotPlaceListResponse(hotPlaceResponses);
    }
}
