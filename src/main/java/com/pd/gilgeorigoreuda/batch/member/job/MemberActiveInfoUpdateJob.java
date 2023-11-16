package com.pd.gilgeorigoreuda.batch.member.job;

import com.pd.gilgeorigoreuda.batch.member.service.MemberActiveInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.batch.core.job.builder.JobBuilder;

@Configuration
@RequiredArgsConstructor
public class MemberActiveInfoUpdateJob extends DefaultBatchConfiguration {

    private final MemberActiveInfoService memberActiveInfoService;

    @Bean
    public Job memberJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("memberActiveJob", jobRepository)
                .start(updateMemberTotalDistanceStep(jobRepository, platformTransactionManager))
                .next(updateMemberTotalVisitCountStep(jobRepository, platformTransactionManager))
                .next(updateMemberExpStep(jobRepository, platformTransactionManager))
                .build();

    }

    @Bean
    public Step updateMemberTotalDistanceStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("updateMemberTotalDistanceStep", jobRepository)
                .tasklet(updateMemberTotalDistanceTask(), platformTransactionManager)
                .build();
    }

    @Bean
    public Step updateMemberTotalVisitCountStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("updateMemberTotalVisitCountStep", jobRepository)
                .tasklet(updateMemberTotalVisitCountTask(), platformTransactionManager)
                .build();
    }

    @Bean
    public Step updateMemberExpStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("updateMemberExpStep", jobRepository)
                .tasklet(updateMemberExpTask(), platformTransactionManager)
                .build();
    }

    public Tasklet updateMemberTotalDistanceTask() {
        return ((contribution, chunkContext) -> {
            memberActiveInfoService.updateMemberTotalDistance();
            return RepeatStatus.FINISHED;
        });
    }

    public Tasklet updateMemberTotalVisitCountTask() {
        return ((contribution, chunkContext) -> {
            memberActiveInfoService.updateMemberTotalVisitCount();
            return RepeatStatus.FINISHED;
        });
    }

    public Tasklet updateMemberExpTask() {
        return ((contribution, chunkContext) -> {
            memberActiveInfoService.updateMemberExp();
            return RepeatStatus.FINISHED;
        });
    }

}
