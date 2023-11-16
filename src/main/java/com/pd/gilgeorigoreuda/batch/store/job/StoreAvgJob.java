package com.pd.gilgeorigoreuda.batch.store.job;

import com.pd.gilgeorigoreuda.batch.store.service.StoreAvgUpdateBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreAvgJob {

    private final StoreAvgUpdateBatchService storeAvgUpdateBatchService;

    public void run() {
        storeAvgUpdateBatchService.updateAllStoreAvgRate();
    }

}
