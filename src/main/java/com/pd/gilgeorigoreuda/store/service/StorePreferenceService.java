package com.pd.gilgeorigoreuda.store.service;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StorePreference;
import com.pd.gilgeorigoreuda.store.domain.entity.StorePreferenceType;
import com.pd.gilgeorigoreuda.store.repository.StorePreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StorePreferenceService {

    private final StorePreferenceRepository storePreferenceRepository;

    public void addStoreLike(Long storeId, Long memberId) {
        Optional<StorePreference> storePreference = storePreferenceRepository.findByStoreIdAndMemberId(storeId, memberId);

        changePreferenceStatus(storePreference, StorePreferenceType.PREFERRED, storeId, memberId);
    }

    public void addStoreHate(Long storeId, Long memberId){
        Optional<StorePreference> storePreference = storePreferenceRepository.findByStoreIdAndMemberId(storeId, memberId);

        changePreferenceStatus(storePreference, StorePreferenceType.NOT_PREFERRED, storeId, memberId);
    }


    private void changePreferenceStatus(Optional<StorePreference> storePreference, StorePreferenceType type, Long storeId, Long memberId) {
        storePreference.ifPresentOrElse(
                existingStorePreference -> {
                    if (existingStorePreference.getPreferenceType().equals(type)) {
                        existingStorePreference.changePreference(StorePreferenceType.NONE);
                    } else {
                        existingStorePreference.changePreference(type);
                    }
                },
                () -> {
                    storePreferenceRepository.save(
                            StorePreference.builder()
                                    .store(Store.builder().id(storeId).build())
                                    .member(Member.builder().id(memberId).build())
                                    .preferenceType(type)
                                    .build()
                    );
                }
        );
    }
}