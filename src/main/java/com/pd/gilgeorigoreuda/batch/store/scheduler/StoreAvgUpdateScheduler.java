package com.pd.gilgeorigoreuda.batch.store.scheduler;

import com.pd.gilgeorigoreuda.batch.store.job.StoreAvgJob;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreAvgUpdateScheduler {

    private final StoreAvgJob storeAvgJob;

    @Scheduled(cron = "0 0 5 * * ?")
    public void updateStoreAvg() {
        storeAvgJob.run();
    }

}
