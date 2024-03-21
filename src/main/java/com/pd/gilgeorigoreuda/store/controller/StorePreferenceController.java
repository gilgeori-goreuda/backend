package com.pd.gilgeorigoreuda.store.controller;

import com.pd.gilgeorigoreuda.auth.annotation.MemberInfo;
import com.pd.gilgeorigoreuda.auth.annotation.MemberOnly;
import com.pd.gilgeorigoreuda.auth.domain.LoginMember;
import com.pd.gilgeorigoreuda.store.service.StorePreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/preferences")
public class StorePreferenceController {

    private final StorePreferenceService storePreferenceService;

    @MemberOnly
    @PostMapping("/stores/{storeId}/like")
    public ResponseEntity<Void> addStoreLike(
            @PathVariable Long storeId,
            @MemberInfo final LoginMember loginMember) {
        storePreferenceService.storeLike(storeId, loginMember.getMemberId());

        return ResponseEntity
                .ok()
                .build();
    }

    @MemberOnly
    @PostMapping("/stores/{storeId}/hate")
    public ResponseEntity<Void> addStoreHate(
            @PathVariable Long storeId,
            @MemberInfo final LoginMember loginMember) {
        storePreferenceService.storeHate(storeId, loginMember.getMemberId());

        return ResponseEntity
                .ok()
                .build();
    }
}
