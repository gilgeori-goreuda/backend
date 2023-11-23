package com.pd.gilgeorigoreuda.visit.controller;

import com.pd.gilgeorigoreuda.auth.MemberInfo;
import com.pd.gilgeorigoreuda.auth.MemberOnly;
import com.pd.gilgeorigoreuda.auth.domain.LoginMember;
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

    @MemberOnly
    @PostMapping("/stores/{storeId}")
    public ResponseEntity<VisitResponse> visit(
            @PathVariable final Long storeId,
            @MemberInfo final LoginMember loginMember,
            @RequestBody @Valid final VisitRequest visitRequest
    ) {
        VisitResponse response = visitService.visit(loginMember.getMemberId(), storeId, visitRequest);

        return ResponseEntity
                .ok()
                .body(response);
    }

    @MemberOnly
    @PostMapping("/verification/stores/{storeId}")
    public ResponseEntity<Void> verifyVisit(
            @PathVariable final Long storeId,
            @MemberInfo final LoginMember loginMember,
            @RequestBody @Valid final VisitVerifyRequest visitVerifyRequest
    ) {
        visitService.verifyVisit(loginMember.getMemberId(), storeId, visitVerifyRequest);

        return ResponseEntity
                .ok()
                .build();
    }

}
