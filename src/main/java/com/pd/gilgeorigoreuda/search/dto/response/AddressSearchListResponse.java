package com.pd.gilgeorigoreuda.search.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AddressSearchListResponse {

    private List<AddressSearchResponse> results;

    private AddressSearchListResponse(List<AddressSearchResponse> results) {
        this.results = results;
    }

    public static AddressSearchListResponse of(List<AddressSearchResponse> results) {
        return new AddressSearchListResponse(results);
    }

}
