package org.prography.kagongsillok.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "카공실록 API",
                description = "카공실록 API 문서입니다"
        ))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi openApi() {
        String[] paths = {"/api/**"};

        return GroupedOpenApi.builder()
                .group("오픈 API")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        String[] paths = {"/admin/**"};

        return GroupedOpenApi.builder()
                .group("어드민 API")
                .pathsToMatch(paths)
                .build();
    }
}
