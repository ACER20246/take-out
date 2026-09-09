package com.sky.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfiguration {
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()

                .group("后台管理系统接口文档")
                .packagesToScan("com.sky.controller")
                .pathsToMatch("/**")
                .build();
    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("苍穹外卖")
                        .description("苍穹外卖接口文档")
                        .version("1.0"));
    }
}
