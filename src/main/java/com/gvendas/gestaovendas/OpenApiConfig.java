package com.gvendas.gestaovendas;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {
    
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("com.gvendas.gestaovendas")
                .pathsToMatch("*")
                .build();
    }
}
