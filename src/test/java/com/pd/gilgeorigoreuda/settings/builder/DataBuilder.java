package com.pd.gilgeorigoreuda.settings.builder;

import com.pd.gilgeorigoreuda.login.domain.MemberToken;
import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.member.domain.entity.MemberActiveInfo;
import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.review.domain.entity.ReviewComment;
import com.pd.gilgeorigoreuda.review.domain.entity.ReviewImage;
import com.pd.gilgeorigoreuda.review.domain.entity.ReviewPreference;
import com.pd.gilgeorigoreuda.statistics.domain.HotPlace;
import com.pd.gilgeorigoreuda.statistics.domain.Keyword;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StorePreference;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreReportHistory;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreVisitRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataBuilder {

    @Autowired
    private BuilderSupporter builderSupporter;

    public MemberToken buildMemberToken(final MemberToken memberToken) {
        return builderSupporter.memberTokenRepository().save(memberToken);
    }

    public MemberActiveInfo buildMemberActiveInfo(final MemberActiveInfo memberActiveInfo) {
        return builderSupporter.memberActiveInfoRepository().save(memberActiveInfo);
    }

    public Member buildMember(final Member member) {
        return builderSupporter.memberRepository().save(member);
    }

    public ReviewComment buildReviewComment(final ReviewComment reviewComment) {
        return builderSupporter.reviewCommentRepository().save(reviewComment);
    }

    public ReviewImage buildReviewImage(final ReviewImage reviewImage) {
        return builderSupporter.reviewImageRepository().save(reviewImage);
    }

    public ReviewPreference buildReviewPreference(final ReviewPreference reviewPreference) {
        return builderSupporter.reviewPreferenceRepository().save(reviewPreference);
    }

    public Review buildReview(final Review review) {
        return builderSupporter.reviewRepository().save(review);
    }

    public HotPlace buildHotPlace(final HotPlace hotPlace) {
        return builderSupporter.hotPlaceRepository().save(hotPlace);
    }

    public Keyword buildKeyword(final Keyword keyword) {
        return builderSupporter.keywordRepository().save(keyword);
    }

    public StorePreference buildStorePreference(final StorePreference storePreference) {
        return builderSupporter.storePreferenceRepository().save(storePreference);
    }

    public StoreReportHistory buildStoreReportHistory(final StoreReportHistory storeReportHistory) {
        return builderSupporter.storeReportHistoryRepository().save(storeReportHistory);
    }

    public Store buildStore(final Store store) {
        return builderSupporter.storeRepository().save(store);
    }

    public StoreVisitRecord buildStoreVisitRecord(final StoreVisitRecord storeVisitRecord) {
        return builderSupporter.storeVisitRecordRepository().save(storeVisitRecord);
    }

}
