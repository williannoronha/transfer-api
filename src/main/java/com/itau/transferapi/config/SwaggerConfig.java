package com.itau.transferapi.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * A classe SwaggerConfig foi criada com o objetivo de disponibiliza uma documentação de API
 * 
 * @author Willian Noronha
 * 
 */
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Transfer API",
        version = "1.0.0",
        description = "API para realizar transferências entre contas bancárias"
    )
)
public class SwaggerConfig {
	
	@Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/v1/**")
                .build();
    }

}
