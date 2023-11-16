package com.pd.gilgeorigoreuda.batch.store.job;

import com.pd.gilgeorigoreuda.batch.store.service.StoreReportAndBlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class StoreReportAndBlockJob extends DefaultBatchConfiguration {

    private final StoreReportAndBlockService storeReportAndBlockService;

    @Bean
    public Job reportJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("reportJob", jobRepository)
                .start(updateAllStoreReportCountStep(jobRepository, platformTransactionManager))
                .next(updateBlockedStoresStep(jobRepository, platformTransactionManager))
                .build();

    }

    @Bean
    public Step updateAllStoreReportCountStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("updateReportStep", jobRepository)
                .tasklet(updateReportTask(), platformTransactionManager)
                .build();
    }

    @Bean
    public Step updateBlockedStoresStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("updateBlockedStoresStep", jobRepository)
                .tasklet(updateBlockedStoresTask(), platformTransactionManager)
                .build();

    }

    public Tasklet updateReportTask() {
        return ((contribution, chunkContext) -> {
            storeReportAndBlockService.updateAllStoreReportCount();
            return RepeatStatus.FINISHED;
        });
    }

    public Tasklet updateBlockedStoresTask() {
        return ((contribution, chunkContext) -> {
            storeReportAndBlockService.updateBlockedStores();
            return RepeatStatus.FINISHED;
        });
    }

}
