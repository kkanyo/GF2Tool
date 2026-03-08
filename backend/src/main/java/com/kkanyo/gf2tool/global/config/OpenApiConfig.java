package com.kkanyo.gf2tool.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GF2 Tool API 명세서")
                        .description("소녀전선2:망명 웹 툴 API")
                        .version("v1.0.0"));
    }
}
