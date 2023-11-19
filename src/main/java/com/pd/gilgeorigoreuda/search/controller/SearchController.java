package com.pd.gilgeorigoreuda.search.controller;

import com.pd.gilgeorigoreuda.search.dto.response.SearchStoreListResponse;
import com.pd.gilgeorigoreuda.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/address")
    public ResponseEntity<SearchStoreListResponse> searchByLatLngAndFoodCategories(
            @RequestParam(required = false, name = "m_lat") final BigDecimal mLat,
            @RequestParam(required = false, name = "m_lng") final BigDecimal mLng,
            @RequestParam(required = false, name = "r_lat") final BigDecimal rLat,
            @RequestParam(required = false, name = "r_lng") final BigDecimal rLng,
            @RequestParam(required = false, name = "foodTypes") final String foodTypes
    ) {
        SearchStoreListResponse response = searchService.searchByLatLngAndFoodCategories(mLat, mLng, rLat, rLng, foodTypes);

        return ResponseEntity
                .ok()
                .body(response);
    }

}
