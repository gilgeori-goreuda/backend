package com.pd.gilgeorigoreuda.home.controller;

import com.pd.gilgeorigoreuda.home.dto.response.PlaceListResponse;
import com.pd.gilgeorigoreuda.home.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/home")
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/newplace")
    public ResponseEntity<PlaceListResponse> getNewPlace() {
        PlaceListResponse response = homeService.getNewPlace();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping("/hotplace")
    public ResponseEntity<PlaceListResponse> getHotPlace() {
        PlaceListResponse response = homeService.getHotPlace();
        
        return ResponseEntity
                .ok()
                .body(response);
    }

}
