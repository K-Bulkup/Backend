package com.kbulkup.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration        // Spring 설정 클래스임을 명시
@EnableSwagger2
public class SwaggerConfig {

    // API 문서 메타 정보 상수
    private final String API_NAME = "Kbulkup";
    private final String API_VERSION = "1.0";
    private final String API_DESCRIPTION = "Kbulkup RestAPI 명세서";

    /**
     * API 문서 기본 정보 설정
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_NAME)                    // API 문서 제목
                .description(API_DESCRIPTION)       // API 문서 설명
                .version(API_VERSION)               // API 버전
                .build();
    }

    /**
     * Swagger 문서 생성 설정
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)    // Swagger 2.0 사용
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))  // @RestController가 붙은 클래스만 문서화 대상으로 지정
                .paths(PathSelectors.any())  // 모든 경로 포함
                .build()
                .apiInfo(apiInfo());         // 위에서 설정한 API 정보 적용
    }
}
