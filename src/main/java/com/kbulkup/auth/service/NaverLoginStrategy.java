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

        // 기존 사용자일 경우, 바로 로그인 처리
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String accessToken = jwtTokenProvider.createAccessToken(user.getUserId(), user.getRoles());
            boolean isNewUser = user.getRoles() == null || user.getRoles().isEmpty(); // 역할이 없으면 신규 사용자처럼 처리
            return LoginResponseDTO.toDTO(user, accessToken, user.getRoles(), isNewUser, LoginType.NAVER.toString(), user.getProviderId());
        }

        // 신규 사용자일 경우
        else {
            User newUser = User.builder()
                    .providerId(naverProfile.getProviderId())
                    .username(naverProfile.getName())
                    .email(naverProfile.getEmail())
                    .loginType(LoginType.NAVER)
                    .build();

            userMapper.saveUser(newUser);

            // 회원가입을 위한 임시 토큰 발급
            String tempAccessToken = jwtTokenProvider.createTempAccessToken(newUser.getUserId());

            // 응답 DTO에 임시 토큰과 함께 신규 사용자임을 나타내는 정보를 담아 보냅니다.
            return LoginResponseDTO.builder()
                    .accessToken(tempAccessToken)
                    .userId(newUser.getUserId())
                    .username(newUser.getUsername())
                    .roles(null)
                    .isNewUser(true)
                    .loginType(LoginType.NAVER.toString())
                    .providerId(naverProfile.getProviderId()) // providerId 설정
                    .build();
        }
    }

    @Override
    public String getLoginType() {
        return LoginType.NAVER;
    }
}