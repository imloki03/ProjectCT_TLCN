package com.hcmute.projectCT.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    //http://localhost:8080/swagger-ui/index.html
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ProjectCT - API Document")
                        .version("v1.0.0")
                        .description("API document for ProjectCT"));
//                .components(new Components()
//                        .addSecuritySchemes(
//                                "bearerAuth",
//                                new SecurityScheme()
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT")))
//                .security(List.of(new SecurityRequirement().addList("bearerAuth")));
    }

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("api-version-1.0")
                .pathsToMatch("/api/v1/**")
                .build();
    }
}
