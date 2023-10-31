package com.pd.gilgeorigoreuda.home.controller;

import com.pd.gilgeorigoreuda.home.dto.response.HotPlaceListResponse;
import com.pd.gilgeorigoreuda.home.dto.response.NewPlaceListResponse;
import com.pd.gilgeorigoreuda.home.service.HomeService;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/home")
public class HomeController {
    private final HomeService homeService;

    @GetMapping("/getNewPlace")
    public ResponseEntity<NewPlaceListResponse> getNewPlace() {

        NewPlaceListResponse response = homeService.getNewPlace();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping("/getHotPlace")
    public ResponseEntity<HotPlaceListResponse> getHotPlace() {

        HotPlaceListResponse response = homeService.getHotPlace();
        
        return ResponseEntity
                .ok()
                .body(response);
    }

}
