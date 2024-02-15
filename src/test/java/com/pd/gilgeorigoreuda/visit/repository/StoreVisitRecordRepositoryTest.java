package com.pd.gilgeorigoreuda.visit.repository;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.settings.RepositoryTest;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreVisitRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.pd.gilgeorigoreuda.settings.fixtures.MemberFixtures.*;
import static com.pd.gilgeorigoreuda.settings.fixtures.StoreFixtures.*;
import static com.pd.gilgeorigoreuda.settings.fixtures.StoreVisitRecordFixtures.*;
import static org.assertj.core.api.SoftAssertions.*;

class StoreVisitRecordRepositoryTest extends RepositoryTest {

    @Test
    @DisplayName("회원 id와 가게 id로 방문기록 조회")
    void findByMemberIdAndStoreId() {
        // given
        Member KIM = dataBuilder.buildMember(KIM());
        Store BUNGEOPPANG = dataBuilder.buildStore(BUNGEOPPANG());
        StoreVisitRecord LEES_VISIT_RECORD = dataBuilder.buildStoreVisitRecord(KIMS_BUNGEOPPANG_VISIT_RECORD());

        // when & then
        storeVisitRecordRepository.findByMemberIdAndStoreId(KIM.getId(), BUNGEOPPANG.getId())
                .ifPresent(storeVisitRecord -> {
                    assertSoftly(softly -> {
                        softly.assertThat(storeVisitRecord.getMember().getId()).isEqualTo(KIM.getId());
                        softly.assertThat(storeVisitRecord.getStore().getId()).isEqualTo(BUNGEOPPANG.getId());
                    });
                });

    }

}