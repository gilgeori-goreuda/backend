package com.pd.gilgeorigoreuda.store.repository;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.settings.RepositoryTest;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.pd.gilgeorigoreuda.settings.fixtures.MemberFixtures.*;
import static com.pd.gilgeorigoreuda.settings.fixtures.StoreFixtures.*;
import static org.assertj.core.api.SoftAssertions.*;
import static org.assertj.core.api.Assertions.*;

class StoreRepositoryTest extends RepositoryTest {

    @Test
    @DisplayName("가게 아이디로 해당가게와 제보자, 가게 카테고리까지 함께 조회")
    void findStoreWithReporterAndCategoryById() {
        // given
        Member KIM = dataBuilder.buildMember(KIM());
        Store BUNGEOPPANG = dataBuilder.buildStore(BUNGEOPPANG());

        // when
        Store store = storeRepository.findStoreWithMemberAndCategories(BUNGEOPPANG.getId()).get();

        // then
        assertSoftly(softly -> {
            softly.assertThat(store.getId()).isEqualTo(BUNGEOPPANG.getId());
            softly.assertThat(store.getName()).isEqualTo(BUNGEOPPANG.getName());
            softly.assertThat(store.getMember().getId()).isEqualTo(KIM.getId());
            softly.assertThat(store.getFoodCategories().size()).isEqualTo(2);
        });
    }

    @Test
    @DisplayName("가게 아이디로 해당가게와 제보자 함께 조회")
    void findStoreWithReporterById() {
        // given
        Member KIM = dataBuilder.buildMember(KIM());
        Store BUNGEOPPANG = dataBuilder.buildStore(BUNGEOPPANG());

        // when
        Store store = storeRepository.findStoreWithMember(BUNGEOPPANG.getId()).get();

        // then
        assertSoftly(softly -> {
            softly.assertThat(store.getId()).isEqualTo(BUNGEOPPANG.getId());
            softly.assertThat(store.getName()).isEqualTo(BUNGEOPPANG.getName());
            softly.assertThat(store.getMember().getId()).isEqualTo(KIM.getId());
        });
    }

    @Test
    @DisplayName("오늘 날짜부터 7일 전까지의 가게를 조회")
    void findStoresByDate() {
        // given
        Member KIM = dataBuilder.buildMember(KIM());
        Member LEE = dataBuilder.buildMember(LEE());
        Store BUNGEOPPANG = dataBuilder.buildStore(BUNGEOPPANG());
        Store ODENG = dataBuilder.buildStore(ODENG());

        // when
        List<Store> stores = storeRepository.findStoresByBetweenDay(LocalDateTime.now().minusDays(7), LocalDateTime.now());

        // then
        assertThat(stores).size().isEqualTo(2);
    }

    @Test
    @DisplayName("가중 평균 평점과 방문 횟수로 정렬하여 상위 10개 가게 조회")
    void findStoresByRateAndVisitCount() {
        // given
        Member KIM = dataBuilder.buildMember(KIM());
        Member LEE = dataBuilder.buildMember(LEE());
        Store BUNGEOPPANG = dataBuilder.buildStore(BUNGEOPPANG());
        Store ODENG = dataBuilder.buildStore(ODENG());

        // when
        List<Store> stores = storeRepository.findStoresByRateAndVisitCount();

        // then
        assertSoftly(softly -> {
            softly.assertThat(stores).isNotNull();
            softly.assertThat(stores.size()).isLessThanOrEqualTo(10);
        });
    }

}