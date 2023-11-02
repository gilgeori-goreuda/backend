package com.pd.gilgeorigoreuda.search.controller;

import com.pd.gilgeorigoreuda.search.dto.response.AddressSearchListResponse;
import com.pd.gilgeorigoreuda.search.service.SearchService;
import com.pd.gilgeorigoreuda.store.domain.entity.FoodType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/address")
    public ResponseEntity<AddressSearchListResponse> addressAndCategorySearch(
            @RequestParam(required = false,name = "lat") Double lat,
            @RequestParam(required = false,name = "lng") Double lng,
            @RequestParam(required = false,name = "foodType") FoodType foodType
            ){

        AddressSearchListResponse response = searchService.getStoreByAddressAndFoodType(lat, lng, foodType);

        return ResponseEntity
                .ok()
                .body(response);
    }

}
