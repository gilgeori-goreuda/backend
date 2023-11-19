package com.pd.gilgeorigoreuda.search.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SearchStoreListResponse {

    private List<SearchStoreResponse> results;

    private SearchStoreListResponse(final List<SearchStoreResponse> results) {
        this.results = results;
    }

    public static SearchStoreListResponse of(final List<SearchStoreResponse> results) {
        return new SearchStoreListResponse(results);
    }

}
