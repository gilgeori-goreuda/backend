package com.pd.gilgeorigoreuda.search.controller;

import com.pd.gilgeorigoreuda.search.dto.response.SearchStoreListResponse;
import com.pd.gilgeorigoreuda.search.resolver.SearchParameter;
import com.pd.gilgeorigoreuda.search.resolver.SearchParams;
import com.pd.gilgeorigoreuda.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/address")
    public ResponseEntity<SearchStoreListResponse> searchByLatLngAndFoodCategories(
            @SearchParams final SearchParameter searchParameter
    ) {
        SearchStoreListResponse response = searchService
                .searchByLatLngAndFoodCategories(
                        searchParameter.getMLat(),
                        searchParameter.getMLng(),
                        searchParameter.getRLat(),
                        searchParameter.getRLng(),
                        searchParameter.getFoodType()
                );

        return ResponseEntity
                .ok()
                .body(response);
    }

}
