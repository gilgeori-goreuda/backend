package com.pd.gilgeorigoreuda.search.controller;

import com.pd.gilgeorigoreuda.search.dto.response.AddressSearchListResponse;
import com.pd.gilgeorigoreuda.search.service.SearchService;
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
    public ResponseEntity<AddressSearchListResponse> addressSearch(
            @RequestParam(required = false,defaultValue = "0",name = "lat") Double lat,
            @RequestParam(required = false,defaultValue = "0",name = "lng") Double lng
    ){

        AddressSearchListResponse response = searchService.getStoreByAddress(lat, lng);

        return ResponseEntity
                .ok()
                .body(response);
    }

}
