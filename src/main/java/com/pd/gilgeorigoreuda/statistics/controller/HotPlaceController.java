package com.pd.gilgeorigoreuda.statistics.controller;

import com.pd.gilgeorigoreuda.statistics.domain.HotPlace;
import com.pd.gilgeorigoreuda.statistics.service.HotPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotPlace")
public class HotPlaceController {
    private final HotPlaceService hotPlaceService;
    @GetMapping
    public List<HotPlace> getAllHotPlace(){
        return hotPlaceService.getAllHotPlace();
    }
}
