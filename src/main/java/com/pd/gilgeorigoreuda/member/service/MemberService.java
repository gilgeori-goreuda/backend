package com.pd.gilgeorigoreuda.member.service;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.member.dto.response.*;
import com.pd.gilgeorigoreuda.member.exception.NoSuchMemberException;
import com.pd.gilgeorigoreuda.member.repository.MemberRepository;
import com.pd.gilgeorigoreuda.review.repository.ReviewRepository;
import com.pd.gilgeorigoreuda.store.repository.StorePreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final ReviewRepository reviewRepository;
    private final StorePreferenceRepository storePreferenceRepository;
    private final MemberRepository memberRepository;

    public MemberReviewListResponse getMyReviews(final Long memberId) {
        List<MemberReviewResponse> memberReviewResponseList = reviewRepository.findAllByMemberId(memberId)
                .stream()
                .map(MemberReviewResponse::of)
                .toList();

        return MemberReviewListResponse.of(memberReviewResponseList);
    }

    public MemberPreferenceStoreListResponse getMyPreferredStores(final Long memberId) {
        List<MemberPreferenceStoreResponse> memberPreferenceStoreResponses = storePreferenceRepository.findMyPreferredStores(memberId)
                .stream()
                .map(MemberPreferenceStoreResponse::of)
                .toList();

        return MemberPreferenceStoreListResponse.of(memberPreferenceStoreResponses);
    }

    public MemberInfoResponse getMemberInfoAndActiveInfo(final Long memberId) {
        checkIsExistMember(memberId);

        return MemberInfoResponse.of(memberRepository.findMemberInfoAndActiveInfoById(memberId));
    }

    private void checkIsExistMember(Long memberId) {
        memberRepository.findById(memberId)
                .orElseThrow(NoSuchMemberException::new);
    }

}