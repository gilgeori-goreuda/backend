package com.pd.gilgeorigoreuda.member.controller;

import com.pd.gilgeorigoreuda.member.dto.response.MemberPreferenceStoreListResponse;
import com.pd.gilgeorigoreuda.member.dto.response.MemberPreferenceStoreResponse;
import com.pd.gilgeorigoreuda.member.dto.response.MemberReviewListResponse;
import com.pd.gilgeorigoreuda.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{memberId}/reviews")
    public ResponseEntity<MemberReviewListResponse> getMyReviews(@PathVariable("memberId") Long memberId) {
        MemberReviewListResponse response = memberService.getMyReviews(memberId);

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping("/{memberId}/preferences")
    public ResponseEntity<MemberPreferenceStoreListResponse> getMyPreference(@PathVariable("memberId") Long memberId) {
        MemberPreferenceStoreListResponse response = memberService.getMyPreference(memberId);

        return ResponseEntity
                .ok()
                .body(response);
    }

}
