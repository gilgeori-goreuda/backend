package com.pd.gilgeorigoreuda.store.controller;

import com.pd.gilgeorigoreuda.store.service.StorePreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/preferences")
public class StorePreferenceController {
    private static final Long TEST_USER_ID = 2L;

    private final StorePreferenceService storePreferenceService;

    @PostMapping("/stores/{storeId}/like")
    public ResponseEntity<Void> addStoreLike(@PathVariable Long storeId) {
        storePreferenceService.addStoreLike(storeId, TEST_USER_ID);

        return ResponseEntity
                .ok()
                .build();
    }
    @PostMapping("/stores/{storeId}/hate")
    public ResponseEntity<Void> addStoreHate(@PathVariable Long storeId) {
        storePreferenceService.addStoreHate(storeId, TEST_USER_ID);

        return ResponseEntity
                .ok()
                .build();
    }
}
