package com.pd.gilgeorigoreuda.statistics.controller;

import com.pd.gilgeorigoreuda.statistics.service.KeywordService;
import com.pd.gilgeorigoreuda.store.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class testController {

    private final KeywordService service;

    @GetMapping
    public void getStore(){
        service.getKeyword();
    }
}
