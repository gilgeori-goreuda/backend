package com.pd.gilgeorigoreuda.visit.controller;

import com.pd.gilgeorigoreuda.visit.dto.request.VisitRequest;
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
    public ResponseEntity<Void> visit(
            // todo: 유저 정보
            @PathVariable final Long storeId,
            @RequestBody @Valid final VisitRequest visitRequest
    ) {
        visitService.visit(1L, storeId, visitRequest);

        return ResponseEntity
                .ok()
                .build();
    }

}
