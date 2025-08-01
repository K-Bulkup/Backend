package com.kbulkup.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.MediaType;

@Configuration
@Getter
@PropertySource("classpath:application-secret.properties")
public class GptConfig {

    @Value("${openai.api.key}")
    private String secretKey;

    @Bean
    public RestTemplate template() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            request.getHeaders().set("Authorization", "Bearer " + secretKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }

}
