package com.kbulkup.payment.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbulkup.common.config.PortOneConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class PortOneClient {

    private final PortOneConfig config;

    private String cachedToken;
    private Instant tokenExpiry;

    /**
     *  AccessToken 발급 및 캐싱
     */
    private String getAccessToken() {
        // 캐싱된 토큰이 유효하면 그대로 사용
        if (cachedToken != null && Instant.now().isBefore(tokenExpiry)) {
            return cachedToken;
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = "{\"imp_key\":\"" + config.getApiKey() + "\",\"imp_secret\":\"" + config.getApiSecret() + "\"}";
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<TokenResponse> response = restTemplate.exchange(
                    config.getBaseUrl() + "/users/getToken",
                    HttpMethod.POST,
                    entity,
                    TokenResponse.class
            );

            TokenResponse.TokenData data = response.getBody() != null ? response.getBody().getResponse() : null;
            if (data == null || data.getAccessToken() == null) {
                throw new IllegalStateException("PortOne returned null AccessToken (response parsing failed)");
            }

            cachedToken = data.getAccessToken();
            tokenExpiry = Instant.now().plusSeconds(data.getExpiredAt());
            return cachedToken;

        } catch (org.springframework.web.client.HttpClientErrorException e) {
            throw e; // 포트원 API 인증 오류 그대로 전파
        } catch (Exception e) {
            throw new RuntimeException("Unknown error while requesting AccessToken", e);
        }
    }

    /**
     *  결제 검증 API 호출
     */
    public PaymentResult verifyPayment(String impUid) {
        String token = getAccessToken();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<PaymentVerificationResponse> response = restTemplate.exchange(
                config.getBaseUrl() + "/payments/" + impUid,
                HttpMethod.GET,
                entity,
                PaymentVerificationResponse.class
        );

        var res = response.getBody().getResponse();
        return new PaymentResult(res.getStatus().equals("paid"), res.getAmount(), res.getPayMethod(), res.getPaidAt());
    }

    /**
     *  Token API 응답 DTO
     */
    private static class TokenResponse {
        @JsonProperty("response")
        private TokenData response;
        public TokenData getResponse() { return response; }

        private static class TokenData {
            @JsonProperty("access_token")
            private String accessToken;
            @JsonProperty("expired_at")
            private long expiredAt;

            public String getAccessToken() { return accessToken; }
            public long getExpiredAt() { return expiredAt - Instant.now().getEpochSecond(); }
        }
    }

    /**
     *  결제 검증 응답 DTO
     */
    private static class PaymentVerificationResponse {
        @JsonProperty("response")
        private PaymentData response;
        public PaymentData getResponse() { return response; }

        private static class PaymentData {
            private String status;
            private int amount;
            @JsonProperty("pay_method")
            private String payMethod;
            @JsonProperty("paid_at")
            private String paidAt;

            public String getStatus() { return status; }
            public int getAmount() { return amount; }
            public String getPayMethod() { return payMethod; }
            public String getPaidAt() { return paidAt; }
        }
    }

    /**
     *  Service에서 사용되는 결과 DTO
     */
    @Getter
    @AllArgsConstructor
    public static class PaymentResult {
        private final boolean paid;
        private final int amount;
        private final String method;
        private final String paidAt;
    }
}
