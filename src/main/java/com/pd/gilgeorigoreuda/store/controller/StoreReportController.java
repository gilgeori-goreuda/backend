package com.pd.gilgeorigoreuda.store.controller;

import com.pd.gilgeorigoreuda.store.dto.request.ReportCreateRequest;
import com.pd.gilgeorigoreuda.store.service.StorePreferenceService;
import com.pd.gilgeorigoreuda.store.service.StoreReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class StoreReportController {

    private static final Long TEST_USER_ID = 2L;

    private final StoreReportService storeReportService;

    @PostMapping("/stores/{storeId}")
    public ResponseEntity<Void> addStoreReport(
            @PathVariable final Long storeId,
            @RequestBody final ReportCreateRequest reportCreateRequest
    ){
        storeReportService.addStoreReport(reportCreateRequest, storeId, TEST_USER_ID);

        return ResponseEntity
                .ok()
                .build();
    }

    @DeleteMapping("/stores/{reportId}")
    public ResponseEntity<Void> deleteStoreReport(
            @PathVariable final Long reportId
    ){
        storeReportService.deleteReport(reportId, TEST_USER_ID);

        return ResponseEntity
                .noContent()
                .build();
    }

}
