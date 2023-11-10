package com.pd.gilgeorigoreuda.statistics.controller;

import com.pd.gilgeorigoreuda.statistics.dto.response.HotPlaceListResponse;
import com.pd.gilgeorigoreuda.statistics.service.HotPlaceService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotplaces")
public class HotPlaceController {

    private final HotPlaceService hotPlaceService;

    @GetMapping
    public ResponseEntity<HotPlaceListResponse> getAllHotPlace() {
        HotPlaceListResponse response = hotPlaceService.getHotPlaces();

        return ResponseEntity
            .ok()
            .body(response);
    }

}
