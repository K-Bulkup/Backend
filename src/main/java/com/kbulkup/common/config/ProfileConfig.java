package com.kbulkup.common.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * 환경별 프로파일 설정 클래스
 */
@Configuration
public class ProfileConfig {

    @Configuration
    @Profile("dev")
    @PropertySource("classpath:/config/application-dev.properties")
    public static class DevConfig {
        // 개발환경 전용 Bean 설정
    }

    @Configuration
    @Profile("prd")
    @PropertySource("classpath:/config/application-prd.properties")
    public static class ProdConfig {
        // 운영환경 전용 Bean 설정
    }
}
