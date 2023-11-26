package com.pd.gilgeorigoreuda.batch.store.scheduler;

import com.pd.gilgeorigoreuda.batch.store.job.StoreVisitJob;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreVisitUpdateScheduler {

    private final StoreVisitJob storeVisitJob;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void updateStoreVisit() {
        storeVisitJob.run();
    }

}
