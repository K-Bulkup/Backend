package com.kbulkup.auth.service;

import com.kbulkup.auth.dto.LoginResponseDTO;
import com.kbulkup.auth.dto.SocialSignupRequestDTO;
import com.kbulkup.auth.dto.SocialUserInfoDto;
import com.kbulkup.common.security.JwtTokenProvider;
import com.kbulkup.user.domain.User;
import com.kbulkup.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SocialSignupService {

    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public LoginResponseDTO completeSignup(SocialSignupRequestDTO requestDTO) {
        // 1. 임시 토큰 검증 및 정보 추출
        if (!jwtTokenProvider.validateToken(requestDTO.getPreAuthToken())) {
            throw new IllegalArgumentException("유효하지 않거나 만료된 토큰입니다.");
        }
        SocialUserInfoDto socialUserInfo = jwtTokenProvider.getSocialUserInfoFromPreAuthToken(requestDTO.getPreAuthToken());

        // 2. 사용자 정보로 최종 회원가입
        User newUser = User.builder()
                .email(socialUserInfo.getEmail())
                .username(socialUserInfo.getUsername())
                .loginType(socialUserInfo.getLoginType())
                .providerId(socialUserInfo.getProviderId())
                .password(passwordEncoder.encode(socialUserInfo.getProviderId())) // 비밀번호는 providerId로 임시 저장
                .roles(Collections.singletonList(requestDTO.getRole()))
                .build();

        userMapper.saveUser(newUser);
        userMapper.saveUserRole(newUser.getUserId(), requestDTO.getRole());

        // 3. 최종 JWT 발급
        String finalAccessToken = jwtTokenProvider.createToken(newUser.getEmail(), newUser.getRoles());

        return LoginResponseDTO.builder()
                .message("회원가입이 성공적으로 완료되었습니다.")
                .accessToken(finalAccessToken)
                .userId(newUser.getUserId())
                .nickname(newUser.getUsername())
                .roles(newUser.getRoles())
                .build();
    }
}
