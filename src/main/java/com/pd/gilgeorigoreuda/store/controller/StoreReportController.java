package com.pd.gilgeorigoreuda.store.controller;

import com.pd.gilgeorigoreuda.auth.MemberInfo;
import com.pd.gilgeorigoreuda.auth.MemberOnly;
import com.pd.gilgeorigoreuda.auth.domain.LoginMember;
import com.pd.gilgeorigoreuda.store.dto.request.ReportCreateRequest;
import com.pd.gilgeorigoreuda.store.dto.response.StoreReportHistoryListResponse;
import com.pd.gilgeorigoreuda.store.service.StoreReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class StoreReportController {

    private final StoreReportService storeReportService;

    @MemberOnly
    @PostMapping("/stores/{storeId}")
    public ResponseEntity<Void> addStoreReport(
            @PathVariable final Long storeId,
            @MemberInfo final LoginMember loginMember,
            @RequestBody final ReportCreateRequest reportCreateRequest
    ) {
        storeReportService.addStoreReport(reportCreateRequest, storeId, loginMember.getMemberId());

        return ResponseEntity
                .ok()
                .build();
    }

    @MemberOnly
    @DeleteMapping("/stores/{reportId}")
    public ResponseEntity<Void> deleteStoreReport(
            @MemberInfo final LoginMember loginMember,
            @PathVariable final Long reportId
    ) {
        storeReportService.deleteReport(reportId, loginMember.getMemberId());

        return ResponseEntity
                .noContent()
                .build();
    }

    @MemberOnly
    @GetMapping("/stores/memberCheck")
    public ResponseEntity<StoreReportHistoryListResponse> checkMemberReportList(
            @MemberInfo final LoginMember loginMember

    ) {
        StoreReportHistoryListResponse storeReportHistoryListResponse =
                storeReportService.checkMemberReportList(loginMember.getMemberId());

        return ResponseEntity
                .ok()
                .body(storeReportHistoryListResponse);
    }

    @GetMapping("/stores/storeCheck/{storeId}")
    public ResponseEntity<StoreReportHistoryListResponse> checkStoreReportList(
            @PathVariable("storeId") Long storeId
    ) {
        StoreReportHistoryListResponse storeReportHistoryListResponse =
                storeReportService.checkStoreReportList(storeId);

        return ResponseEntity
                .ok()
                .body(storeReportHistoryListResponse);
    }

}
