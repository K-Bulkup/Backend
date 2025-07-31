package com.kbulkup.payment.client;

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
        if (cachedToken != null && Instant.now().isBefore(tokenExpiry)) {
            return cachedToken;
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = "{\"imp_key\":\"" + config.getApiKey() + "\",\"imp_secret\":\"" + config.getApiSecret() + "\"}";
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<TokenResponse> response = restTemplate.exchange(
                config.getBaseUrl() + "/users/getToken",
                HttpMethod.POST,
                entity,
                TokenResponse.class
        );

        TokenResponse.TokenData data = response.getBody().getResponse();
        cachedToken = data.getAccessToken();
        tokenExpiry = Instant.now().plusSeconds(data.getExpiredAt());

        return cachedToken;
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

    /**  내부 DTO (Token 응답) */
    private static class TokenResponse {
        private TokenData response;
        public TokenData getResponse() { return response; }
        private static class TokenData {
            private String access_token;
            private long expired_at;
            public String getAccessToken() { return access_token; }
            public long getExpiredAt() { return expired_at - Instant.now().getEpochSecond(); }
        }
    }

    /**  내부 DTO (결제 검증 응답) */
    private static class PaymentVerificationResponse {
        private PaymentData response;
        public PaymentData getResponse() { return response; }
        private static class PaymentData {
            private String status;
            private int amount;
            private String pay_method;
            private String paid_at;
            public String getStatus() { return status; }
            public int getAmount() { return amount; }
            public String getPayMethod() { return pay_method; }
            public String getPaidAt() { return paid_at; }
        }
    }

    /**  Service에서 사용되는 결과 DTO */
    @Getter
    @AllArgsConstructor
    public static class PaymentResult {
        private final boolean paid;
        private final int amount;
        private final String method;
        private final String paidAt;
    }
}
