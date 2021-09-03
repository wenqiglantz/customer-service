package com.github.wenqiglantz.service.customerservice.restcontroller;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    private static final String TITLE = "Customer Service";
    private static final String DESCRIPTION = "Application used for customer service";
    private static final String VERSION = "1.0";
    private static final String URL = "https://github.com/wenqiglantz";

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("customerservice")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI customerServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(TITLE)
                        .description(DESCRIPTION)
                        .version(VERSION)
                        .license(new License().name("Apache 2.0").url(URL)))
                .externalDocs(new ExternalDocumentation()
                        .description("Customer service Wiki Documentation")
                        .url("https://github.com/wenqiglantz"));
    }
}
