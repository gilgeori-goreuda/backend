package com.pd.gilgeorigoreuda.settings.restdocs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.snippet.Attributes.*;

@Configuration
public class RestDocsConfiguration {

    public static Attribute field(final String key, final String value) {
        return new Attribute(key, value);
    }

    @Bean
    public RestDocumentationResultHandler write() {
        return document(
                "{class-name}/{method-name}",
                preprocessRequest(
                        removeHeaders("Content-Length", "Host"),
                        prettyPrint()
                ),
                preprocessResponse(
                        removeHeaders("Transfer-Encoding", "Date", "Keep-Alive", "Connection"),
                        prettyPrint()
                )
        );
    }

}
