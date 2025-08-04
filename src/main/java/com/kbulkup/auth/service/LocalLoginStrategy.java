package com.kbulkup.auth.service;

import com.kbulkup.auth.domain.LoginType;
import com.kbulkup.auth.dto.request.LoginRequestDTO;
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.common.exception.AuthException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.common.security.JwtTokenProvider;
import com.kbulkup.user.mapper.UserMapper;
import com.kbulkup.user.domain.User;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class LocalLoginStrategy implements LoginStrategy {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LocalLoginStrategy(UserMapper userMapper, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public LoginResponseDTO login(Object request) {
        LoginRequestDTO requestDTO = (LoginRequestDTO) request;
        // 1. 이메일로 사용자(User)를 찾음 (Mapper 사용)
        User user = userMapper.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new AuthException(ResponseCode.AUTH_EMAIL_NOT_FOUND));

        // 2. 사용자가 존재하지 않는 경우 예외 발생 // 이미 위에서 처리

        // 3. 비밀번호 일치 여부 확인
        String plainPassword = requestDTO.getPassword();
        String hashedPasswordFromDB = user.getPassword();
        boolean matches = passwordEncoder.matches(plainPassword, hashedPasswordFromDB);

        log.info("--- 비밀번호 비교 로깅 ---");
        log.info("사용자 입력 비밀번호: {}", plainPassword);
        log.info("DB 저장된 해시: {}", hashedPasswordFromDB);
        log.info("비교 결과: {}", matches);
        log.info("----------------------");

        if (!matches) {
            throw new AuthException(ResponseCode.AUTH_INVALID_PASSWORD);
        }

        // 4. JWT 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getUserId(), user.getRoles());

        // 5. 응답 DTO 반환
        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .isNewUser(false)
                .userId(user.getUserId())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }

    @Override
    public String getLoginType() {
        return LoginType.LOCAL;
    }
}