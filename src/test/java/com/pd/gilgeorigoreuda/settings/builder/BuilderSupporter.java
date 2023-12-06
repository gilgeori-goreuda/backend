package com.pd.gilgeorigoreuda.settings.builder;

import com.pd.gilgeorigoreuda.login.repository.MemberTokenRepository;
import com.pd.gilgeorigoreuda.member.repository.MemberActiveInfoRepository;
import com.pd.gilgeorigoreuda.member.repository.MemberRepository;
import com.pd.gilgeorigoreuda.review.repository.ReviewCommentRepository;
import com.pd.gilgeorigoreuda.review.repository.ReviewImageRepository;
import com.pd.gilgeorigoreuda.review.repository.ReviewPreferenceRepository;
import com.pd.gilgeorigoreuda.review.repository.ReviewRepository;
import com.pd.gilgeorigoreuda.statistics.repository.HotPlaceRepository;
import com.pd.gilgeorigoreuda.statistics.repository.KeywordRepository;
import com.pd.gilgeorigoreuda.store.repository.StorePreferenceRepository;
import com.pd.gilgeorigoreuda.store.repository.StoreReportHistoryRepository;
import com.pd.gilgeorigoreuda.store.repository.StoreRepository;
import com.pd.gilgeorigoreuda.visit.repository.StoreVisitRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuilderSupporter {

    @Autowired
    private MemberTokenRepository memberTokenRepository;

    @Autowired
    private MemberActiveInfoRepository memberActiveInfoRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewCommentRepository reviewCommentRepository;

    @Autowired
    private ReviewImageRepository reviewImageRepository;

    @Autowired
    private ReviewPreferenceRepository reviewPreferenceRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private HotPlaceRepository hotPlaceRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private StorePreferenceRepository storePreferenceRepository;

    @Autowired
    private StoreReportHistoryRepository storeReportHistoryRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreVisitRecordRepository storeVisitRecordRepository;

    public MemberTokenRepository memberTokenRepository() {
        return memberTokenRepository;
    }

    public MemberActiveInfoRepository memberActiveInfoRepository() {
        return memberActiveInfoRepository;
    }

    public MemberRepository memberRepository() {
        return memberRepository;
    }

    public ReviewCommentRepository reviewCommentRepository() {
        return reviewCommentRepository;
    }

    public ReviewImageRepository reviewImageRepository() {
        return reviewImageRepository;
    }

    public ReviewPreferenceRepository reviewPreferenceRepository() {
        return reviewPreferenceRepository;
    }

    public ReviewRepository reviewRepository() {
        return reviewRepository;
    }

    public HotPlaceRepository hotPlaceRepository() {
        return hotPlaceRepository;
    }

    public KeywordRepository keywordRepository() {
        return keywordRepository;
    }

    public StorePreferenceRepository storePreferenceRepository() {
        return storePreferenceRepository;
    }

    public StoreReportHistoryRepository storeReportHistoryRepository() {
        return storeReportHistoryRepository;
    }

    public StoreRepository storeRepository() {
        return storeRepository;
    }

    public StoreVisitRecordRepository storeVisitRecordRepository() {
        return storeVisitRecordRepository;
    }

}
