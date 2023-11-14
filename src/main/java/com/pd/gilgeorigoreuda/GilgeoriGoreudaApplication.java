package com.pd.gilgeorigoreuda;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableBatchProcessing
public class GilgeoriGoreudaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GilgeoriGoreudaApplication.class, args);
    }

}
