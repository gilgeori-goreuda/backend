package com.pd.gilgeorigoreuda.store.service;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StorePreference;
import com.pd.gilgeorigoreuda.store.domain.entity.StorePreferenceType;
import com.pd.gilgeorigoreuda.store.repository.StorePreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorePreferenceService {
    private final StorePreferenceRepository storePreferenceRepository;
    public void addStoreLike(Long storeId, Long memberId){
        storePreferenceRepository.save(StorePreference
                .builder()
                        .store(Store
                                .builder()
                                .id(storeId)
                                .build())
                        .member(Member
                                .builder()
                                .id(memberId)
                                .build())
                        .preferenceType(StorePreferenceType.PREFERRED)
                .build());
    }

    public void addStoreHate(Long storeId, Long memberId){
        storePreferenceRepository.save(StorePreference
                .builder()
                .store(Store
                        .builder()
                        .id(storeId)
                        .build())
                .member(Member
                        .builder()
                        .id(memberId)
                        .build())
                .preferenceType(StorePreferenceType.NOT_PREFERRED)
                .build());
    }
}
