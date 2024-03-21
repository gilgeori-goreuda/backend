package com.pd.gilgeorigoreuda.store.service;

import com.pd.gilgeorigoreuda.settings.ServiceTest;
import com.pd.gilgeorigoreuda.store.domain.entity.StorePreference;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.pd.gilgeorigoreuda.settings.fixtures.StorePreferenceFixtures.*;
import static com.pd.gilgeorigoreuda.store.domain.entity.StorePreferenceType.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.*;
import static org.mockito.BDDMockito.*;

class StorePreferenceServiceTest extends ServiceTest {

    @Nested
    @DisplayName("가게 좋아요")
    class StoreLikeTest {

        @Test
        @DisplayName("가게 좋아요 정보가 없다면 가게 좋아요 생성 후 저장")
        void saveStorePreferenceWhenNotExists() {
            // given
            given(storePreferenceRepository.findByStoreIdAndMemberId(any(), any()))
                    .willReturn(Optional.empty());

            // when
            storePreferenceService.storeLike(anyLong(), anyLong());

            // then
            assertSoftly(softly -> {
                then(storePreferenceRepository).should().findByStoreIdAndMemberId(any(), any());
                then(storePreferenceRepository).should().save(any());
            });
        }

        @Test
        @DisplayName("가게 좋아요 정보가 있다면 가게 좋아요 정보 변경 - PREFERRED -> NONE")
        void changeStorePreferenceWhenPreferred() {
            // given
            StorePreference preferencePreferred = BUNGEOPPANG_PREFERENCE_PREFERRED();
            Long storeId = preferencePreferred.getStore().getId();
            Long memberId = preferencePreferred.getMember().getId();

            given(storePreferenceRepository.findByStoreIdAndMemberId(any(), any()))
                    .willReturn(Optional.of(preferencePreferred));

            // when
            storePreferenceService.storeLike(storeId, memberId);

            // then
            assertSoftly(softly -> {
                then(storePreferenceRepository).should().findByStoreIdAndMemberId(any(), any());
                then(storePreferenceRepository).should(never()).save(preferencePreferred);
            });

            storePreferenceRepository.findByStoreIdAndMemberId(storeId, memberId)
                    .ifPresent(storePreference -> {
                        assertThat(storePreference.getPreferenceType()).isEqualTo(NONE);
                    });
        }

        @Test
        @DisplayName("가게 좋아요 정보가 있다면 가게 좋아요 정보 변경 - NONE -> PREFERRED")
        void changeStorePreferenceWhenNone() {
            // given
            StorePreference preferenceNone = BUNGEOPPANG_PREFERENCE_NONE();
            Long storeId = preferenceNone.getStore().getId();
            Long memberId = preferenceNone.getMember().getId();

            given(storePreferenceRepository.findByStoreIdAndMemberId(any(), any()))
                    .willReturn(Optional.of(preferenceNone));

            // when
            storePreferenceService.storeLike(storeId, memberId);

            // then
            assertSoftly(softly -> {
                then(storePreferenceRepository).should().findByStoreIdAndMemberId(any(), any());
                then(storePreferenceRepository).should(never()).save(preferenceNone);
            });

            storePreferenceRepository.findByStoreIdAndMemberId(storeId, memberId)
                    .ifPresent(storePreference -> {
                        assertThat(storePreference.getPreferenceType()).isEqualTo(PREFERRED);
                    });
        }

        @Test
        @DisplayName("가게 싫어요 정보가 있다면 가게 좋아요 정보 변경 - NOT_PREFERRED -> PREFERRED")
        void changeStorePreferenceWhenNotPreferred() {
            // given
            StorePreference preferenceNotPreferred = BUNGEOPPANG_PREFERENCE_NOT_PREFERRED();
            Long storeId = preferenceNotPreferred.getStore().getId();
            Long memberId = preferenceNotPreferred.getMember().getId();

            given(storePreferenceRepository.findByStoreIdAndMemberId(any(), any()))
                    .willReturn(Optional.of(preferenceNotPreferred));

            // when
            storePreferenceService.storeLike(storeId, memberId);

            // then
            assertSoftly(softly -> {
                then(storePreferenceRepository).should().findByStoreIdAndMemberId(any(), any());
                then(storePreferenceRepository).should(never()).save(preferenceNotPreferred);
            });

            storePreferenceRepository.findByStoreIdAndMemberId(storeId, memberId)
                    .ifPresent(storePreference -> {
                        assertThat(storePreference.getPreferenceType()).isEqualTo(PREFERRED);
                    });
        }

    }

    @Nested
    @DisplayName("가게 싫어요")
    class StoreHateTest {

        @Test
        @DisplayName("가게 싫어요 정보가 없다면 가게 싫어요 생성 후 저장")
        void saveStorePreferenceWhenNotExists() {
            // given
            given(storePreferenceRepository.findByStoreIdAndMemberId(any(), any()))
                    .willReturn(Optional.empty());

            // when
            storePreferenceService.storeHate(anyLong(), anyLong());

            // then
            assertSoftly(softly -> {
                then(storePreferenceRepository).should().findByStoreIdAndMemberId(any(), any());
                then(storePreferenceRepository).should().save(any());
            });
        }

        @Test
        @DisplayName("가게 싫어요 정보가 있다면 가게 싫어요 정보 변경 - NOT_PREFERRED -> NONE")
        void changeStorePreferenceWhenNotPreferred() {
            // given
            StorePreference preferenceNotPreferred = ODENG_PREFERENCE_NOT_PREFERRED();
            Long storeId = preferenceNotPreferred.getStore().getId();
            Long memberId = preferenceNotPreferred.getMember().getId();

            given(storePreferenceRepository.findByStoreIdAndMemberId(any(), any()))
                    .willReturn(Optional.of(preferenceNotPreferred));

            // when
            storePreferenceService.storeHate(storeId, memberId);

            // then
            assertSoftly(softly -> {
                then(storePreferenceRepository).should().findByStoreIdAndMemberId(any(), any());
                then(storePreferenceRepository).should(never()).save(preferenceNotPreferred);
            });

            storePreferenceRepository.findByStoreIdAndMemberId(storeId, memberId)
                    .ifPresent(storePreference -> {
                        assertThat(storePreference.getPreferenceType()).isEqualTo(NONE);
                    });
        }

        @Test
        @DisplayName("가게 싫어요 정보가 있다면 가게 싫어요 정보 변경 - NONE -> NOT_PREFERRED")
        void changeStorePreferenceWhenNone() {
            // given
            StorePreference preferenceNone = ODENG_PREFERENCE_NONE();
            Long storeId = preferenceNone.getStore().getId();
            Long memberId = preferenceNone.getMember().getId();

            given(storePreferenceRepository.findByStoreIdAndMemberId(any(), any()))
                    .willReturn(Optional.of(preferenceNone));

            // when
            storePreferenceService.storeHate(storeId, memberId);

            // then
            assertSoftly(softly -> {
                then(storePreferenceRepository).should().findByStoreIdAndMemberId(any(), any());
                then(storePreferenceRepository).should(never()).save(preferenceNone);
            });

            storePreferenceRepository.findByStoreIdAndMemberId(storeId, memberId)
                    .ifPresent(storePreference -> {
                        assertThat(storePreference.getPreferenceType()).isEqualTo(NOT_PREFERRED);
                    });
        }

        @Test
        @DisplayName("가게 좋아요 정보가 있다면 가게 싫어요 정보 변경 - PREFERRED -> NOT_PREFERRED")
        void changeStorePreferenceWhenPreferred() {
            // given
            StorePreference preferencePreferred = ODENG_PREFERENCE_PREFERRED();
            Long storeId = preferencePreferred.getStore().getId();
            Long memberId = preferencePreferred.getMember().getId();

            given(storePreferenceRepository.findByStoreIdAndMemberId(any(), any()))
                    .willReturn(Optional.of(preferencePreferred));

            // when
            storePreferenceService.storeHate(storeId, memberId);

            // then
            assertSoftly(softly -> {
                then(storePreferenceRepository).should().findByStoreIdAndMemberId(any(), any());
                then(storePreferenceRepository).should(never()).save(preferencePreferred);
            });

            storePreferenceRepository.findByStoreIdAndMemberId(storeId, memberId)
                    .ifPresent(storePreference -> {
                        assertThat(storePreference.getPreferenceType()).isEqualTo(NOT_PREFERRED);
                    });
        }

    }

}