package com.pd.gilgeorigoreuda.common.config.web;

import org.apache.http.HttpHeaders;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // todo: 모든 출처 요청 허용 보안상 위험 -> 실제 운영 환경에서는 * 대신에 허용하려는 출처(도메인)를 명시
    private static final String LOCALHOST_FRONTEND = "http://localhost:3000";
    private static final String DEV_SERVER = "https://dev.gilgeorigoreuda.com";
    private static final String PROD_SERVER = "https://gilgeorigoreuda.com";

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(PROD_SERVER, DEV_SERVER, LOCALHOST_FRONTEND)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowCredentials(true)
                .exposedHeaders(HttpHeaders.LOCATION);

        WebMvcConfigurer.super.addCorsMappings(registry);
    }

}
