package com.kbulkup.payment.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbulkup.common.config.PortOneConfig;
import com.kbulkup.common.exception.BaseException;
import com.kbulkup.common.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class PortOneClient {

    private final PortOneConfig config;

    private String cachedToken;
    private Instant tokenExpiry;

    /**
     * AccessToken 발급 및 캐싱
     */
    private String getAccessToken() {
        // 만료 60초 전부터는 재발급
        if (cachedToken != null && tokenExpiry != null && Instant.now().isBefore(tokenExpiry.minusSeconds(60))) {
            return cachedToken;
        }

        log.info("[PortOneClient] AccessToken 요청 시도...");
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

            TokenResponse tokenResponse = response.getBody();
            TokenResponse.TokenData data = (tokenResponse != null) ? tokenResponse.getResponse() : null;
            if (data == null || data.getAccessToken() == null) {
                log.error("[PortOneClient] 토큰 응답 데이터 없음");
                throw new BaseException(ResponseCode.PAYMENT_VERIFICATION_FAILED);
            }

            cachedToken = data.getAccessToken();
            tokenExpiry = Instant.ofEpochSecond(data.getExpiredAt());
            log.info("[PortOneClient] AccessToken 발급 성공, 만료시간={}", tokenExpiry);
            return cachedToken;

        } catch (HttpClientErrorException e) {
            log.error("[PortOneClient] 포트원 인증 오류: {}", e.getMessage());
            throw new BaseException(ResponseCode.PAYMENT_VERIFICATION_FAILED);
        } catch (Exception e) {
            log.error("[PortOneClient] AccessToken 발급 실패", e);
            throw new BaseException(ResponseCode.PAYMENT_PROCESS_FAILED);
        }
    }

    /**
     * 결제 검증 API 호출
     */
    public PaymentResult verifyPayment(String impUid) {
        try {
            String token = getAccessToken();

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            // 기존 코드 유지(환경에 따라 Bearer/비Bearer 모두 허용됨)
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<PaymentVerificationResponse> response = restTemplate.exchange(
                    config.getBaseUrl() + "/payments/" + impUid,
                    HttpMethod.GET,
                    entity,
                    PaymentVerificationResponse.class
            );

            PaymentVerificationResponse body = response.getBody();
            if (body == null || body.getResponse() == null) {
                log.error("[PortOneClient] 결제 검증 응답이 비어있음");
                throw new BaseException(ResponseCode.PAYMENT_VERIFICATION_FAILED);
            }
            PaymentVerificationResponse.PaymentData res = body.getResponse();

            log.info("[PortOneClient] 결제 검증 성공: impUid={}, status={}", impUid, res.getStatus());

            boolean paid = "paid".equalsIgnoreCase(res.getStatus());
            return new PaymentResult(
                    paid,
                    res.getAmount(),
                    res.getPayMethod(),
                    String.valueOf(res.getPaidAt()) // unix epoch를 문자열로 전달
            );

        } catch (HttpClientErrorException e) {
            log.error("[PortOneClient] 결제 검증 실패(포트원 응답 오류): {}", e.getMessage());
            throw new BaseException(ResponseCode.PAYMENT_VERIFICATION_FAILED);
        } catch (Exception e) {
            log.error("[PortOneClient] 결제 검증 실패", e);
            throw new BaseException(ResponseCode.PAYMENT_PROCESS_FAILED);
        }
    }

    /** Token API 응답 DTO */
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
            public long getExpiredAt() { return expiredAt; }
        }
    }

    /** 결제 검증 응답 DTO */
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
            private long paidAt; // ✅ number 매핑

            public String getStatus() { return status; }
            public int getAmount() { return amount; }
            public String getPayMethod() { return payMethod; }
            public long getPaidAt() { return paidAt; }
        }
    }

    /** 서비스에서 사용되는 결과 DTO */
    @Getter
    @AllArgsConstructor
    public static class PaymentResult {
        private final boolean paid;
        private final int amount;
        private final String method;
        private final String paidAt;
    }
}
