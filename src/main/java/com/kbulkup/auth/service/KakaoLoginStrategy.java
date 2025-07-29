package com.kbulkup.auth.service;

import com.kbulkup.auth.domain.LoginType;
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.auth.kakao.KakaoApiClient;
import com.kbulkup.auth.kakao.dto.KakaoProfileResponse;
import com.kbulkup.auth.kakao.dto.KakaoTokenResponse; // 추가
import com.kbulkup.common.security.JwtTokenProvider;
import com.kbulkup.user.domain.User;
import com.kbulkup.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class KakaoLoginStrategy implements LoginStrategy {

    private final UserMapper userMapper;
    private final KakaoApiClient kakaoApiClient;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginResponseDTO login(Object code) {
        // 1. 인가 코드로 카카오 액세스 토큰 발급
        KakaoTokenResponse kakaoTokenResponse = kakaoApiClient.getAccessToken((String) code);

        // 2. 액세스 토큰으로 카카오 사용자 프로필 정보 조회
        KakaoProfileResponse kakaoProfile = kakaoApiClient.getKakaoProfile(kakaoTokenResponse.getAccessToken());

        Optional<User> userOptional = userMapper.findByProviderIdAndLoginType(
                kakaoProfile.getProviderId(),
                LoginType.KAKAO
        );

        // 기존 사용자일 경우
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String accessToken = jwtTokenProvider.createAccessToken(user.getUserId(), user.getRoles());
            return LoginResponseDTO.builder()
                    .accessToken(accessToken)
                    .userId(user.getUserId())
                    .username(user.getUsername())
                    .isNewUser(false)
                    .build();
        }
        // 신규 사용자일 경우
        else {
            User newUser = User.builder()
                    .providerId(kakaoProfile.getProviderId())
                    .username(kakaoProfile.getNickname())
                    .email(kakaoProfile.getEmail())
                    .loginType(LoginType.KAKAO)
                    .roles(Collections.emptyList())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            userMapper.saveUser(newUser);

            String tempAccessToken = jwtTokenProvider.createTempAccessToken(newUser.getUserId());

            return LoginResponseDTO.builder()
                    .accessToken(tempAccessToken)
                    .userId(newUser.getUserId())
                    .username(newUser.getUsername())
                    .isNewUser(true)
                    .build();
        }
    }

    @Override
    public String getLoginType() {
        return LoginType.KAKAO;
    }
}