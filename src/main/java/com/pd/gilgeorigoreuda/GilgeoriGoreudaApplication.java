package com.pd.gilgeorigoreuda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GilgeoriGoreudaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GilgeoriGoreudaApplication.class, args);
    }

}
