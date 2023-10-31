package com.pd.gilgeorigoreuda.home.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NewPlaceListResponse {

    private List<NewPlaceResponse> recommendNewPlace;

    private NewPlaceListResponse(List<NewPlaceResponse> recommendNewPlace) {
        this.recommendNewPlace = recommendNewPlace;
    }

    public static NewPlaceListResponse of(List<NewPlaceResponse> recommendNewPlace) {
        return new NewPlaceListResponse(recommendNewPlace);
    }
}
