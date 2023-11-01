package com.pd.gilgeorigoreuda.member.controller;

import com.pd.gilgeorigoreuda.member.dto.response.MemberPreferenceResponse;
import com.pd.gilgeorigoreuda.member.dto.response.MemberReviewListResponse;
import com.pd.gilgeorigoreuda.member.service.MemberService;
import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.store.domain.entity.StorePreference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

//    @GetMapping("/{memberId}")
//    public ResponseEntity<Member> get(@PathVariable("memberId") Long memberId) {
//        Member member = memberService.getMember(memberId);
//        return ResponseEntity.ok().body(member);
//    }

    @GetMapping("/{memberId}/review")
    public ResponseEntity<MemberReviewListResponse> getMyReviews(@PathVariable("memberId") Long memberId) {
        MemberReviewListResponse response = memberService.getMyReviews(memberId);

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping("/{memberId}/preference")
    public ResponseEntity<MemberPreferenceResponse> getMyPreference(@PathVariable("memberId") Long memberId) {
        MemberPreferenceResponse response = memberService.getMyPreference(memberId);

        return ResponseEntity
                .ok()
                .body(response);
    }
}
