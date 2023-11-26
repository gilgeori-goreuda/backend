package com.pd.gilgeorigoreuda.store.controller;

import com.pd.gilgeorigoreuda.auth.MemberInfo;
import com.pd.gilgeorigoreuda.auth.MemberOnly;
import com.pd.gilgeorigoreuda.auth.domain.LoginMember;
import com.pd.gilgeorigoreuda.store.dto.request.StoreCreateRequest;
import com.pd.gilgeorigoreuda.store.dto.request.StoreUpdateRequest;
import com.pd.gilgeorigoreuda.store.dto.response.StoreCreateResponse;
import com.pd.gilgeorigoreuda.store.dto.response.StoreResponse;
import com.pd.gilgeorigoreuda.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    @MemberOnly
    @PostMapping
    public ResponseEntity<Void> createStore(
            @MemberInfo final LoginMember loginMember,
            @Valid @RequestBody final StoreCreateRequest storeCreateRequest
    ) {
        StoreCreateResponse storeCreateResponse = storeService.saveStore(loginMember.getMemberId(), storeCreateRequest);

        return ResponseEntity
                .created(URI.create("/api/v1/stores/" + storeCreateResponse.getId()))
                .build();
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponse> getStore(
            @PathVariable final Long storeId,
            @RequestParam(required = false, name = "lat") final BigDecimal lat,
            @RequestParam(required = false, name = "lng") final BigDecimal lng
    ) {
        StoreResponse storeResponse = storeService.getStore(storeId, lat, lng);

        return ResponseEntity
                .ok()
                .body(storeResponse);
    }

    @MemberOnly
    @PutMapping("/{storeId}")
    public ResponseEntity<Void> updateStore(
            @MemberInfo final LoginMember loginMember,
            @Valid @RequestBody final StoreUpdateRequest storeUpdateRequest,
            @PathVariable final Long storeId
    ) {
        storeService.updateStore(loginMember.getMemberId(), storeId, storeUpdateRequest);

        return ResponseEntity
                .noContent()
                .build();
    }

    @MemberOnly
    @DeleteMapping("/{storeId}")
    public ResponseEntity<Void> deleteStore(
            @MemberInfo final LoginMember loginMember,
            @PathVariable final Long storeId
    ) {
        storeService.deleteStore(loginMember.getMemberId(), storeId);

        return ResponseEntity
                .noContent()
                .build();
    }

}
