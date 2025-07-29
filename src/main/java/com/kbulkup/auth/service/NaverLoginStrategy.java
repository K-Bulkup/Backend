package com.kbulkup.auth.service;

import com.kbulkup.auth.domain.LoginType;
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.common.security.JwtTokenProvider;
import com.kbulkup.user.domain.User;
import com.kbulkup.user.mapper.UserMapper;
import com.kbulkup.auth.naver.NaverApiClient;
import com.kbulkup.auth.naver.dto.NaverProfile;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NaverLoginStrategy implements LoginStrategy {

    private final UserMapper userMapper;
    private final NaverApiClient naverApiClient;
    private final JwtTokenProvider jwtTokenProvider;

    public NaverLoginStrategy(UserMapper userMapper, NaverApiClient naverApiClient, JwtTokenProvider jwtTokenProvider) {
        this.userMapper = userMapper;
        this.naverApiClient = naverApiClient;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public LoginResponseDTO login(Object code) {
        NaverProfile naverProfile = naverApiClient.getNaverProfile((String) code);

        Optional<User> userOptional = userMapper.findByProviderIdAndLoginType(naverProfile.getProviderId(), LoginType.NAVER);

        User user;
        // 신규 사용자일 경우 DB에 저장
        if (userOptional.isEmpty()) {
            user = User.builder()
                    .providerId(naverProfile.getProviderId())
                    .username(naverProfile.getName())
                    .email(naverProfile.getEmail())
                    .loginType(LoginType.NAVER)
                    .build();
            userMapper.saveUser(user);
            // 새로 생성된 사용자는 역할이 없으므로 DB에서 다시 조회할 필요 없음
            user.setRoles(new java.util.ArrayList<>());
        } else {
            // 기존 사용자의 경우 DB에서 최신 정보 조회
            user = userMapper.findById(userOptional.get().getUserId())
                    .orElseThrow(() -> new com.kbulkup.common.exception.BaseException(com.kbulkup.common.response.ResponseCode.USER_NOT_FOUND));
        }

        // 항상 임시 토큰을 발급하여 역할 선택 화면으로 유도
        String tempAccessToken = jwtTokenProvider.createTempAccessToken(user.getUserId());

        // 프론트엔드에서 역할 선택 화면을 렌더링할 수 있도록 필요한 정보를 모두 담아 전달
        return LoginResponseDTO.builder()
                .accessToken(tempAccessToken) // 임시 토큰
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles() != null ? user.getRoles() : java.util.Collections.emptyList()) // 현재 역할 목록
                .isNewUser(true) // 역할 선택이 필요하다는 플래그
                .loginType(LoginType.NAVER.toString())
                .providerId(user.getProviderId())
                .build();
    }

    @Override
    public String getLoginType() {
        return LoginType.NAVER;
    }
}