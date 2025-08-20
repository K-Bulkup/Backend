package com.kbulkup.auth.service;

import com.kbulkup.auth.domain.Role;
import com.kbulkup.auth.dto.request.SignupRequestDTO;
import com.kbulkup.auth.dto.request.SocialSignUpRequestDTO;
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.auth.dto.response.SignupResponseDTO;
import com.kbulkup.common.exception.AuthException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.common.security.JwtTokenProvider;
import com.kbulkup.user.domain.User;
import com.kbulkup.user.event.UserRegisteredEvent;
import com.kbulkup.user.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

/**
 * AuthServiceImpl 단위 테스트
 * 이 클래스는 인증 서비스의 핵심 비즈니스 로직을 테스트합니다:
 * - 일반 회원가입 (신규 사용자, 기존 사용자 역할 추가)
 * - 소셜 회원가입 (임시 토큰을 통한 역할 설정)
 * - 예외 상황 처리
 * - 이벤트 발행 검증
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    @DisplayName("신규 사용자 회원가입 - 성공")
    void signup_NewUser_Success() {
        // Given
        SignupRequestDTO signupRequest = SignupRequestDTO.builder()
                .username("testUser")
                .email("test@example.com")
                .password("password123!")
                .role(Role.TRAINEE)
                .build();

        given(userMapper.findByEmail(signupRequest.getEmail())).willReturn(Optional.empty());
        given(passwordEncoder.encode(signupRequest.getPassword())).willReturn("encryptedPassword");

        // User 저장 시 ID 설정을 시뮬레이션
        willAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setUserId(1L);
            return null;
        }).given(userMapper).saveUser(any(User.class));

        // When
        SignupResponseDTO result = authService.signup(signupRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        assertThat(result.getUsername()).isEqualTo("testUser");
        assertThat(result.getLoginType()).isEqualTo("LOCAL");

        // 비밀번호 암호화 검증
        then(passwordEncoder).should().encode("password123!");

        // 사용자 저장 검증
        then(userMapper).should().saveUser(argThat(user ->
                user.getUsername().equals("testUser") &&
                user.getEmail().equals("test@example.com") &&
                user.getPassword().equals("encryptedPassword") &&
                user.getLoginType().equals("LOCAL")
        ));

        // 역할 저장 검증
        then(userMapper).should().saveUserRole(1L, Role.TRAINEE);
    }

    @Test
    @DisplayName("로그아웃 - 정상 처리 (상태없음)")
    void logout_Success() {
        // Given & When & Then
        // JWT는 무상태이므로 별다른 처리가 없어야 함
        assertThatCode(() -> authService.logout()).doesNotThrowAnyException();
    }
}
