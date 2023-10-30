package com.pd.gilgeorigoreuda.store.controller;

import com.pd.gilgeorigoreuda.store.service.StoreFavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store")
public class StoreFavoriteController {

    private final StoreFavoriteService storeFavoriteService;

    @PostMapping("/{storeId}/storeFavorite")
    public void addFavoriteStore(@PathVariable Long storeId){
        storeFavoriteService.addFavoriteStore(storeId);
    }
}
