package com.pd.gilgeorigoreuda.batch.store.service;

import com.pd.gilgeorigoreuda.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreAvgUpdateBatchService {

    private final StoreRepository storeRepository;

    public void updateAllStoreAvgRate() {
       storeRepository.updateAllStoresAvgRating();
    }

}
