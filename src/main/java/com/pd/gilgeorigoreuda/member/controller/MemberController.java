package com.pd.gilgeorigoreuda.member.controller;

import com.pd.gilgeorigoreuda.auth.annotation.MemberInfo;
import com.pd.gilgeorigoreuda.auth.annotation.MemberOnly;
import com.pd.gilgeorigoreuda.auth.domain.LoginMember;
import com.pd.gilgeorigoreuda.member.dto.response.MemberInfoResponse;
import com.pd.gilgeorigoreuda.member.dto.response.MemberPreferenceStoreListResponse;
import com.pd.gilgeorigoreuda.member.dto.response.MemberReviewListResponse;
import com.pd.gilgeorigoreuda.member.service.MemberService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    
    @MemberOnly
    @GetMapping("/reviews")
    public ResponseEntity<MemberReviewListResponse> getMyReviews(
            @MemberInfo final LoginMember loginMember
    ) {
        MemberReviewListResponse response = memberService.getMyReviews(loginMember.getMemberId());

        return ResponseEntity
                .ok()
                .body(response);
    }

    @MemberOnly
    @GetMapping("/preferences")
    public ResponseEntity<MemberPreferenceStoreListResponse> getMyPreferredStores(
            @MemberInfo final LoginMember loginMember
    ) {
        MemberPreferenceStoreListResponse response = memberService.getMyPreferredStores(loginMember.getMemberId());

        return ResponseEntity
                .ok()
                .body(response);
    }

    @MemberOnly
    @GetMapping
    public ResponseEntity<MemberInfoResponse> getMemberInfoAndActiveInfo(
            @MemberInfo final LoginMember loginMember
    ) {
        MemberInfoResponse response = memberService.getMemberInfoAndActiveInfo(loginMember.getMemberId());

        return ResponseEntity
                .ok()
                .body(response);
    }

}
