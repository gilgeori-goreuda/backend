package com.pd.gilgeorigoreuda.member.service;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.member.dto.response.MemberPreferenceResponse;
import com.pd.gilgeorigoreuda.member.dto.response.MemberReviewListResponse;
import com.pd.gilgeorigoreuda.member.dto.response.MemberReviewResponse;
import com.pd.gilgeorigoreuda.member.repository.MemberRepository;
import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.review.repository.ReviewRepository;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StorePreferenceType;
import com.pd.gilgeorigoreuda.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    public Member getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        return member;
    }

    public MemberReviewListResponse getMyReviews(Long memberId) {
        List<MemberReviewResponse> memberReviewResponseList = reviewRepository.findAllByMemberId(memberId)
                .stream()
                .map(MemberReviewResponse::of)
                .toList();

        // 필요없는것까지 싹다 가져와서 response에서 걸려줘야 한다.
        return MemberReviewListResponse.of(memberReviewResponseList);
    }

    public MemberPreferenceResponse getMyPreference(Long memberId) {
        List<Store> allByMemberId = storeRepository.findAllByMemberIdAndLike(memberId);


        return MemberPreferenceResponse.of(allByMemberId);

    }

}