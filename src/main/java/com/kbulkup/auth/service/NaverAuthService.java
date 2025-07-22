package com.kbulkup.auth.service;

import com.kbulkup.auth.dto.SocialLoginResponseDTO;
import com.kbulkup.auth.dto.SocialUserInfoDto;
import com.kbulkup.common.security.JwtTokenProvider;
import com.kbulkup.user.domain.User;
import com.kbulkup.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NaverAuthService {

    @Value("${naver.client.id}")
    private String naverClientId;
    @Value("${naver.client.secret}")
    private String naverClientSecret;
    @Value("${naver.callback.url}")
    private String naverCallbackUrl;

    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SocialLoginResponseDTO naverLogin(String code, String state) {
        String accessToken = getNaverAccessToken(code, state);
        Map<String, Object> naverUserProfile = getNaverUserProfile(accessToken);

        String providerId = (String) naverUserProfile.get("id");
        String email = (String) naverUserProfile.get("email");
        String username = (String) naverUserProfile.get("nickname");
        String loginType = "NAVER";

        // Provider ID로만 사용자 조회
        Optional<User> existingUserOptional = userMapper.findByProviderIdAndLoginType(providerId, loginType);

        if (existingUserOptional.isPresent()) {
            // 최종적으로 사용자가 존재하면: 로그인 성공
            User existingUser = existingUserOptional.get();
            String finalAccessToken = jwtTokenProvider.createToken(existingUser.getEmail(), existingUser.getRoles());

            return SocialLoginResponseDTO.builder()
                    .status(SocialLoginResponseDTO.LoginStatus.LOGIN_SUCCESS)
                    .accessToken(finalAccessToken)
                    .userId(existingUser.getUserId())
                    .nickname(existingUser.getUsername())
                    .roles(existingUser.getRoles())
                    .build();
        } else {
            // 신규 사용자: 회원가입 필요
            SocialUserInfoDto newUserInfo = SocialUserInfoDto.builder()
                    .email(email)
                    .providerId(providerId)
                    .loginType(loginType)
                    .username(username)
                    .build();

            String preAuthToken = jwtTokenProvider.createPreAuthToken(newUserInfo);

            return SocialLoginResponseDTO.builder()
                    .status(SocialLoginResponseDTO.LoginStatus.SIGNUP_REQUIRED)
                    .preAuthToken(preAuthToken)
                    .build();
        }
    }

    private String getNaverAccessToken(String code, String state) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", naverClientId);
        params.add("client_secret", naverClientSecret);
        params.add("code", code);
        params.add("state", state);
        params.add("redirect_uri", naverCallbackUrl);

        HttpEntity<MultiValueMap<String, String>> naverTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                naverTokenRequest,
                Map.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return (String) response.getBody().get("access_token");
        } else {
            throw new RuntimeException("네이버 Access Token 발급 실패: " + response.getBody());
        }
    }

    private Map<String, Object> getNaverUserProfile(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> naverProfileRequest = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                naverProfileRequest,
                Map.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return (Map<String, Object>) response.getBody().get("response");
        } else {
            throw new RuntimeException("네이버 프로필 정보 조회 실패: " + response.getBody());
        }
    }
}
