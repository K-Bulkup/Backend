package com.kbulkup.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:config/application-dev.properties") // ✅ resources/config 로드 강제
public class PortOneConfig {

    private final String apiKey;
    private final String apiSecret;
    private final String baseUrl;

    public PortOneConfig(
            @Value("${PORTONE_API_KEY:#{null}}") String envApiKey,
            @Value("${portone.api.key:#{null}}") String propApiKey,
            @Value("${PORTONE_API_SECRET:#{null}}") String envApiSecret,
            @Value("${portone.api.secret:#{null}}") String propApiSecret,
            @Value("${portone.api.url:https://api.iamport.kr}") String propBaseUrl) {

        // 환경변수가 우선, 없으면 properties fallback
        this.apiKey = envApiKey != null ? envApiKey : propApiKey;
        this.apiSecret = envApiSecret != null ? envApiSecret : propApiSecret;
        this.baseUrl = propBaseUrl; // URL은 기본값 제공
    }
}
