package com.kbulkup.auth.service;

import com.kbulkup.auth.dto.LoginRequestDTO;
import com.kbulkup.auth.dto.LoginResponseDTO;
import com.kbulkup.auth.mapper.AuthMapper;
import com.kbulkup.common.security.JwtTokenProvider;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = authMapper.findByEmailAndLoginType(loginRequestDTO.getEmail(), "LOCAL")
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일이거나 소셜 로그인 계정입니다."));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String accesstoken = jwtTokenProvider.createToken(user.getEmail(), user.getRoles());

        return LoginResponseDTO.builder()
                .message("로그인 성공")
                .accessToken(accesstoken)
                .userId(user.getUserId())
                .nickname(user.getUsername())
                .roles(user.getRoles())
                .build();
    }
}