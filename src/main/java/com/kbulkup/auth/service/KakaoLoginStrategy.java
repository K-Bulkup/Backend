package com.kbulkup.auth.service;

import com.kbulkup.auth.domain.LoginType;
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.auth.kakao.KakaoApiClient;
import com.kbulkup.auth.kakao.dto.KakaoProfileResponse;
import com.kbulkup.auth.kakao.dto.KakaoTokenResponse; // 추가
import com.kbulkup.common.exception.AuthException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.common.security.JwtTokenProvider;
import com.kbulkup.user.domain.User;
import com.kbulkup.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

import static java.util.Collections.emptyList;

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

        User user;
        // 신규 사용자일 경우 DB에 저장
        if (userOptional.isEmpty()) {
            user = User.builder()
                    .providerId(kakaoProfile.getProviderId())
                    .username(kakaoProfile.getNickname() != null ? kakaoProfile.getNickname() : "")
                    .loginType(LoginType.KAKAO)
                    .build();
            userMapper.saveUser(user);
            // 새로 생성된 사용자는 역할이 없으므로 DB에서 다시 조회할 필요 없음
            user.setRoles(new java.util.ArrayList<>());
        } else {
            // 기존 사용자의 경우 DB에서 최신 정보 조회
            user = userMapper.findById(userOptional.get().getUserId())
                    .orElseThrow(() -> new AuthException(ResponseCode.AUTH_USER_NOT_FOUND));
        }

        // 항상 임시 토큰을 발급하여 역할 선택 화면으로 유도
        String tempAccessToken = jwtTokenProvider.createTempAccessToken(user.getEmail(), user.getUserId());

        // 프론트엔드에서 역할 선택 화면을 렌더링할 수 있도록 필요한 정보를 모두 담아 전달
        return LoginResponseDTO.builder()
                .accessToken(tempAccessToken) // 임시 토큰
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles() != null ? user.getRoles() : emptyList()) // 현재 역할 목록
                .isNewUser(true) // 역할 선택이 필요하다는 플래그
                .loginType(LoginType.KAKAO.toString())
                .providerId(user.getProviderId())
                .build();
    }

    @Override
    public String getLoginType() {
        return LoginType.KAKAO;
    }
}