package com.pd.gilgeorigoreuda.common.config;

import com.pd.gilgeorigoreuda.common.decorater.MdcTaskDecorator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(8);
        taskExecutor.setMaxPoolSize(50);
        taskExecutor.setQueueCapacity(200);

        taskExecutor.setTaskDecorator(new MdcTaskDecorator());
        taskExecutor.setThreadNamePrefix("async-task-");
        taskExecutor.setThreadGroupName("async-group");

        return taskExecutor;
    }

}