package com.kbulkup.auth.naver;

import com.kbulkup.auth.naver.dto.NaverProfile;
import com.kbulkup.auth.naver.dto.NaverProfileResponse;
import com.kbulkup.auth.naver.dto.NaverTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class NaverApiClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${oauth2.naver.client-id}")
    private String clientId;

    @Value("${oauth2.naver.client-secret}")
    private String clientSecret;

    @Value("${oauth2.naver.redirect-uri}")
    private String redirectUri;

    public String getClientId() {
        return clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    /**
     * 인가 코드를 받아 네이버 사용자 정보를 가져오는 메서드.
     *
     * @param authorizationCode 네이버로부터 받은 인가 코드
     * @return 네이버 사용자 프로필 정보
     */
    public NaverProfile getNaverProfile(String authorizationCode) {
        String accessToken = getAccessToken(authorizationCode);
        return getProfileWithToken(accessToken);
    }

    private String getAccessToken(String authorizationCode) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("code", authorizationCode);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        String tokenUrl = "https://nid.naver.com/oauth2.0/token";
        NaverTokenResponse response = restTemplate.postForObject(tokenUrl, requestEntity, NaverTokenResponse.class);

        if (response == null) {
            throw new IllegalArgumentException("네이버 액세스 토큰 응답이 null입니다.");
        }

        if (response.getError() != null) {
            throw new IllegalArgumentException("네이버 액세스 토큰 발급 실패: " + response.getErrorDescription() + " (" + response.getError() + ")");
        }

        if (response.getAccessToken() == null) {
            throw new IllegalArgumentException("네이버 액세스 토큰이 응답에 포함되어 있지 않습니다.");
        }
        return response.getAccessToken();
    }

    private NaverProfile getProfileWithToken(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String profileUrl = "https://openapi.naver.com/v1/nid/me";
        NaverProfileResponse response = restTemplate.exchange(
                profileUrl,
                HttpMethod.GET,
                requestEntity,
                NaverProfileResponse.class
        ).getBody();

        if (response == null || response.getResponse() == null) {
            throw new IllegalArgumentException("네이버 사용자 정보 조회에 실패했습니다.");
        }
        return response.getResponse();
    }
}