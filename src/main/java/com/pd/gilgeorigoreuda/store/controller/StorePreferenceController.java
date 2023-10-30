package com.pd.gilgeorigoreuda.store.controller;

import com.pd.gilgeorigoreuda.store.service.StorePreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store")
public class StorePreferenceController {
    private static final Long TEST_USER_ID = 2L;

    private final StorePreferenceService storePreferenceService;

    @PostMapping("/{storeId}/like")
    public void addStoreLike(@PathVariable Long storeId){
        storePreferenceService.addStoreLike(storeId, TEST_USER_ID);
    }
    @PostMapping("/{storeId}/hate")
    public void addStoreHate(@PathVariable Long storeId){
        storePreferenceService.addStoreHate(storeId, TEST_USER_ID);
    }
}
