package com.pd.gilgeorigoreuda.batch.store.service;

import com.pd.gilgeorigoreuda.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreReportAndBlockService {

    private final StoreRepository storeRepository;

    public void updateAllStoreReportCount() {
        storeRepository.updateAllStoresReportCount();
    }

    public void updateBlockedStores() {
        final int REPORT_COUNT = 1;
        storeRepository.updateBlockedStore(REPORT_COUNT);
    }

}
