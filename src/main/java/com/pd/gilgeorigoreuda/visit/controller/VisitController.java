package com.pd.gilgeorigoreuda.visit.controller;

import com.pd.gilgeorigoreuda.visit.dto.request.VisitRequest;
import com.pd.gilgeorigoreuda.visit.dto.request.VisitVerifyRequest;
import com.pd.gilgeorigoreuda.visit.dto.response.VisitResponse;
import com.pd.gilgeorigoreuda.visit.service.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/visit")
public class VisitController {

    private final VisitService visitService;

    // todo: 권한 확인
    @PostMapping("/stores/{storeId}")
    public ResponseEntity<VisitResponse> visit(
            // todo: 유저 정보
            @PathVariable final Long storeId,
            @RequestBody @Valid final VisitRequest visitRequest
    ) {
        VisitResponse response = visitService.visit(1L, storeId, visitRequest);

        return ResponseEntity
                .ok()
                .body(response);
    }

    // todo: 권한 확인
    @PostMapping("/verification/stores/{storeId}")
    public ResponseEntity<Void> verifyVisit(
            // todo: 유저 정보
            @PathVariable final Long storeId,
            @RequestBody @Valid final VisitVerifyRequest visitVerifyRequest
    ) {
        visitService.verifyVisit(1L, storeId, visitVerifyRequest);

        return ResponseEntity
                .ok()
                .build();
    }

}
