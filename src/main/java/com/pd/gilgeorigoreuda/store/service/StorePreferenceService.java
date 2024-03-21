package com.pd.gilgeorigoreuda.store.service;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StorePreference;
import com.pd.gilgeorigoreuda.store.domain.entity.StorePreferenceType;
import com.pd.gilgeorigoreuda.store.repository.StorePreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.pd.gilgeorigoreuda.store.domain.entity.StorePreferenceType.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StorePreferenceService {

    private final StorePreferenceRepository storePreferenceRepository;

    @Transactional
    public void storeLike(final Long storeId, final Long memberId) {
        storePreferenceRepository.findByStoreIdAndMemberId(storeId, memberId)
                        .ifPresentOrElse(
                                storePreference -> changePreferenceStatus(storePreference, PREFERRED),
                                () -> saveStorePreference(PREFERRED, storeId, memberId)
                        );
    }

    @Transactional
    public void storeHate(final Long storeId, final Long memberId){
        storePreferenceRepository.findByStoreIdAndMemberId(storeId, memberId)
                .ifPresentOrElse(
                        storePreference -> changePreferenceStatus(storePreference, NOT_PREFERRED),
                        () -> saveStorePreference(NOT_PREFERRED, storeId, memberId)
                );

    }

    private void changePreferenceStatus(final StorePreference storePreference, final StorePreferenceType type) {
        if (storePreference.getPreferenceType().equals(type)) {
            storePreference.changePreference(NONE);
        } else {
            storePreference.changePreference(type);
        }
    }

    private void saveStorePreference (final StorePreferenceType type, final Long storeId, final Long memberId) {
        storePreferenceRepository.save(
                StorePreference.builder()
                        .store(Store.builder().id(storeId).build())
                        .member(Member.builder().id(memberId).build())
                        .preferenceType(type)
                        .build()
        );
    }

}