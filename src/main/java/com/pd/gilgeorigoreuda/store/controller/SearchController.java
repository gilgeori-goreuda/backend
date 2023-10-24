package com.pd.gilgeorigoreuda.store.controller;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/store")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService service;

    @GetMapping("search")
    public void getStore(@RequestParam String keyword){
        service.getStore(keyword);
    }

}
