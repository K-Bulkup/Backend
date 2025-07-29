package com.kbulkup.auth.kakao;

import com.kbulkup.auth.kakao.dto.KakaoProfileResponse;
import com.kbulkup.auth.kakao.dto.KakaoTokenResponse; // 추가
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient; // WebClient 사용 예시

@Component
public class KakaoApiClient {

    @Value("${kakao.client-id}")
    private String kakaoClientId;

    @Value("${kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Value("${kakao.token-uri}")
    private String kakaoTokenUri; // 토큰 발급 URL

    @Value("${kakao.user-info-uri}")
    private String kakaoUserInfoUri; // 사용자 정보 URL

    private final WebClient webClient; // WebClient 주입

    public KakaoApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("").build(); // WebClient 인스턴스 생성
    }

    public String getClientId() {
        return kakaoClientId;
    }

    public String getRedirectUri() {
        return kakaoRedirectUri;
    }

    // 인가 코드를 사용하여 액세스 토큰을 받아오는 메서드
    public KakaoTokenResponse getAccessToken(String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", kakaoClientId);
        formData.add("redirect_uri", kakaoRedirectUri);
        formData.add("code", code);

        return webClient.post()
                .uri(kakaoTokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(KakaoTokenResponse.class)
                .block(); // 실제 서비스에서는 block() 대신 non-blocking 방식 권장
    }

    // 액세스 토큰을 사용하여 사용자 프로필 정보를 받아오는 메서드
    public KakaoProfileResponse getKakaoProfile(String accessToken) {
        return webClient.get()
                .uri(kakaoUserInfoUri)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoProfileResponse.class)
                .block(); // 실제 서비스에서는 block() 대신 non-blocking 방식 권장
    }
}