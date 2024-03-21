package com.pd.gilgeorigoreuda.store.repository;

import com.pd.gilgeorigoreuda.settings.RepositoryTest;
import com.pd.gilgeorigoreuda.store.domain.entity.StorePreference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.pd.gilgeorigoreuda.settings.fixtures.MemberFixtures.*;
import static com.pd.gilgeorigoreuda.settings.fixtures.StoreFixtures.*;
import static com.pd.gilgeorigoreuda.settings.fixtures.StorePreferenceFixtures.*;
import static com.pd.gilgeorigoreuda.store.domain.entity.StorePreferenceType.*;
import static org.assertj.core.api.SoftAssertions.*;

class StorePreferenceRepositoryTest extends RepositoryTest {

    @BeforeEach
    void setUp() {
        dataBuilder.buildMembers(List.of(KIM(), LEE()));
        dataBuilder.buildStores(List.of(BUNGEOPPANG(), ODENG()));
    }

    @Test
    @DisplayName("memberId로 선호 가게 리스트 조회 - 'PREFERRED(선호)' 타입만 조회")
    void findMyPreferredStores() {
        // given
        dataBuilder.buildStorePreferences(List.of(ODENG_PREFERENCE_PREFERRED(), ODENG_PREFERENCE_NONE(), ODENG_PREFERENCE_NOT_PREFERRED()));

        // when
        List<StorePreference> storePreferences = storePreferenceRepository.findMyPreferredStores(KIM().getId());

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(storePreferences.size()).isEqualTo(1);
                    storePreferences.forEach(storePreference -> {
                        softly.assertThat(storePreference.getPreferenceType()).isEqualTo(PREFERRED);
                    });
                }
        );
    }

    @Test
    @DisplayName("storeId와 memberId로 선호 가게 조회")
    void findByStoreIdAndMemberId() {
        // given
        StorePreference storePreference = dataBuilder.buildStorePreference(BUNGEOPPANG_PREFERENCE_PREFERRED());

        // when & then
        storePreferenceRepository.findByStoreIdAndMemberId(storePreference.getStore().getId(), storePreference.getMember().getId())
                .ifPresent(
                        result -> {
                            assertSoftly(
                                    softly -> {
                                        softly.assertThat(result.getStore().getId()).isEqualTo(storePreference.getStore().getId());
                                        softly.assertThat(result.getMember().getId()).isEqualTo(storePreference.getMember().getId());
                                        softly.assertThat(result.getPreferenceType()).isEqualTo(storePreference.getPreferenceType());
                                    }
                            );
                        }
                );
    }

}