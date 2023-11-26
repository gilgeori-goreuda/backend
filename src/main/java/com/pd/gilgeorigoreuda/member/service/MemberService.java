package com.pd.gilgeorigoreuda.member.service;

import com.pd.gilgeorigoreuda.member.dto.response.MemberPreferenceStoreListResponse;
import com.pd.gilgeorigoreuda.member.dto.response.MemberPreferenceStoreResponse;
import com.pd.gilgeorigoreuda.member.dto.response.MemberReviewListResponse;
import com.pd.gilgeorigoreuda.member.dto.response.MemberReviewResponse;
import com.pd.gilgeorigoreuda.review.repository.ReviewRepository;
import com.pd.gilgeorigoreuda.store.repository.StorePreferenceRepository;
import com.pd.gilgeorigoreuda.store.repository.StoreRepository;
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

    public MemberReviewListResponse getMyReviews(final Long memberId) {
        List<MemberReviewResponse> memberReviewResponseList = reviewRepository.findAllByMemberId(memberId)
                .stream()
                .map(MemberReviewResponse::of)
                .toList();

        return MemberReviewListResponse.of(memberReviewResponseList);
    }

    public MemberPreferenceStoreListResponse getMyPreference(final Long memberId) {
        List<MemberPreferenceStoreResponse> memberPreferenceStoreResponses = storePreferenceRepository.findMyPreferredStores(memberId)
                .stream()
                .map(MemberPreferenceStoreResponse::of)
                .toList();

        return MemberPreferenceStoreListResponse.of(memberPreferenceStoreResponses);
    }

}