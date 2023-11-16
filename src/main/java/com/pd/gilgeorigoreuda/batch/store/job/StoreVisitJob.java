package com.pd.gilgeorigoreuda.batch.store.job;

import com.pd.gilgeorigoreuda.batch.store.service.StoreVisitUpdateBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreVisitJob {

    private final StoreVisitUpdateBatchService storeVisitUpdateBatchService;

    public void run() {
        storeVisitUpdateBatchService.updateAllStoreVisit();
    }

}
