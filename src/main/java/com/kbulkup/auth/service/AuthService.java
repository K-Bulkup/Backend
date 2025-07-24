package com.kbulkup.auth.service;

import com.kbulkup.auth.dto.request.LoginRequestDTO;
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.user.mapper.UserMapper;
import com.kbulkup.common.security.JwtTokenProvider;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userMapper.findByEmailAndLoginType(loginRequestDTO.getEmail(), "LOCAL")
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일이거나 소셜 로그인 계정입니다."));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        // 요청된 역할(role)이 사용자의 실제 역할 목록에 포함되어 있는지 확인
        String requestedRole = loginRequestDTO.getRole();
        if (requestedRole == null || user.getRoles().stream().noneMatch(r -> r.equalsIgnoreCase(requestedRole))) {
            throw new IllegalArgumentException("요청한 역할(" + requestedRole + ")로 로그인할 수 없습니다. 사용자의 역할 목록에 없습니다.");
        }

        // 요청된 역할만 포함하여 JWT 토큰 생성
        String accesstoken = jwtTokenProvider.createToken(user.getEmail(), Collections.singletonList(requestedRole));

        return LoginResponseDTO.builder()
                .message("로그인 성공")
                .accessToken(accesstoken)
                .userId(user.getUserId())
                .nickname(user.getUsername())
                .roles(Collections.singletonList(requestedRole)) // 응답에도 요청된 역할만 포함
                .build();
    }
}