package com.pd.gilgeorigoreuda.store.controller;

import com.pd.gilgeorigoreuda.store.dto.request.ReportCreateRequest;
import com.pd.gilgeorigoreuda.store.service.StorePreferenceService;
import com.pd.gilgeorigoreuda.store.service.StoreReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store/{storeId}/report")
public class StoreReportController {
    private static final Long TEST_USER_ID = 2L;

    private final StoreReportService storeReportService;

    @PostMapping
    public void addStoreReport(@PathVariable Long storeId,
                               @RequestBody ReportCreateRequest reportCreateRequest){
        storeReportService.addStoreReport(reportCreateRequest, storeId, TEST_USER_ID);
    }

}
