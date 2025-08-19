package com.kbulkup.user.service;

import com.kbulkup.common.security.JwtTokenProvider;
import com.kbulkup.user.dto.response.UserResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

/**
 * UserServiceImpl 단위 테스트
 * 이 클래스는 사용자 서비스의 핵심 비즈니스 로직을 테스트합니다:
 * - JWT 토큰에서 사용자 정보 추출
 * - 사용자 ID 및 역할 정보 반환
 * - 다양한 역할 조합 처리
 * - 토큰 파싱 결과 검증
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("JWT 토큰에서 사용자 정보 추출 - 일반 사용자")
    void getUserFromToken_WithUserRole_Success() {
        // Given
        String token = "valid.jwt.token";
        Long expectedUserId = 1L;
        List<String> expectedRoles = Arrays.asList("ROLE_USER");

        given(jwtTokenProvider.getUserId(token)).willReturn(expectedUserId);
        given(jwtTokenProvider.getRoles(token)).willReturn(expectedRoles);

        // When
        UserResponseDTO result = userService.getUserFromToken(token);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(expectedUserId);
        assertThat(result.getRoles()).isEqualTo(expectedRoles);
        assertThat(result.getRoles()).hasSize(1);
        assertThat(result.getRoles()).contains("ROLE_USER");

        // Mock 호출 검증
        then(jwtTokenProvider).should().getUserId(token);
        then(jwtTokenProvider).should().getRoles(token);
    }

    @Test
    @DisplayName("JWT 토큰에서 사용자 정보 추출 - 트레이너 역할")
    void getUserFromToken_WithTrainerRole_Success() {
        // Given
        String token = "trainer.jwt.token";
        Long expectedUserId = 2L;
        List<String> expectedRoles = Arrays.asList("ROLE_TRAINER");

        given(jwtTokenProvider.getUserId(token)).willReturn(expectedUserId);
        given(jwtTokenProvider.getRoles(token)).willReturn(expectedRoles);

        // When
        UserResponseDTO result = userService.getUserFromToken(token);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(expectedUserId);
        assertThat(result.getRoles()).isEqualTo(expectedRoles);
        assertThat(result.getRoles()).hasSize(1);
        assertThat(result.getRoles()).contains("ROLE_TRAINER");

        // Mock 호출 검증
        then(jwtTokenProvider).should().getUserId(token);
        then(jwtTokenProvider).should().getRoles(token);
    }

    @Test
    @DisplayName("JWT 토큰에서 사용자 정보 추출 - 관리자 역할")
    void getUserFromToken_WithAdminRole_Success() {
        // Given
        String token = "admin.jwt.token";
        Long expectedUserId = 3L;
        List<String> expectedRoles = Arrays.asList("ROLE_ADMIN");

        given(jwtTokenProvider.getUserId(token)).willReturn(expectedUserId);
        given(jwtTokenProvider.getRoles(token)).willReturn(expectedRoles);

        // When
        UserResponseDTO result = userService.getUserFromToken(token);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(expectedUserId);
        assertThat(result.getRoles()).isEqualTo(expectedRoles);
        assertThat(result.getRoles()).hasSize(1);
        assertThat(result.getRoles()).contains("ROLE_ADMIN");

        // Mock 호출 검증
        then(jwtTokenProvider).should().getUserId(token);
        then(jwtTokenProvider).should().getRoles(token);
    }

    @Test
    @DisplayName("JWT 토큰에서 사용자 정보 추출 - 다중 역할")
    void getUserFromToken_WithMultipleRoles_Success() {
        // Given
        String token = "multi.role.jwt.token";
        Long expectedUserId = 4L;
        List<String> expectedRoles = Arrays.asList("ROLE_USER", "ROLE_TRAINER");

        given(jwtTokenProvider.getUserId(token)).willReturn(expectedUserId);
        given(jwtTokenProvider.getRoles(token)).willReturn(expectedRoles);

        // When
        UserResponseDTO result = userService.getUserFromToken(token);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(expectedUserId);
        assertThat(result.getRoles()).isEqualTo(expectedRoles);
        assertThat(result.getRoles()).hasSize(2);
        assertThat(result.getRoles()).contains("ROLE_USER", "ROLE_TRAINER");

        // Mock 호출 검증
        then(jwtTokenProvider).should().getUserId(token);
        then(jwtTokenProvider).should().getRoles(token);
    }

    @Test
    @DisplayName("JWT 토큰에서 사용자 정보 추출 - 빈 역할 리스트")
    void getUserFromToken_WithEmptyRoles_Success() {
        // Given
        String token = "empty.roles.jwt.token";
        Long expectedUserId = 5L;
        List<String> expectedRoles = Collections.emptyList();

        given(jwtTokenProvider.getUserId(token)).willReturn(expectedUserId);
        given(jwtTokenProvider.getRoles(token)).willReturn(expectedRoles);

        // When
        UserResponseDTO result = userService.getUserFromToken(token);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(expectedUserId);
        assertThat(result.getRoles()).isEqualTo(expectedRoles);
        assertThat(result.getRoles()).isEmpty();

        // Mock 호출 검증
        then(jwtTokenProvider).should().getUserId(token);
        then(jwtTokenProvider).should().getRoles(token);
    }

    @Test
    @DisplayName("JWT 토큰에서 사용자 정보 추출 - null 역할 리스트")
    void getUserFromToken_WithNullRoles_Success() {
        // Given
        String token = "null.roles.jwt.token";
        Long expectedUserId = 6L;
        List<String> expectedRoles = null;

        given(jwtTokenProvider.getUserId(token)).willReturn(expectedUserId);
        given(jwtTokenProvider.getRoles(token)).willReturn(expectedRoles);

        // When
        UserResponseDTO result = userService.getUserFromToken(token);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(expectedUserId);
        assertThat(result.getRoles()).isNull();

        // Mock 호출 검증
        then(jwtTokenProvider).should().getUserId(token);
        then(jwtTokenProvider).should().getRoles(token);
    }

    @Test
    @DisplayName("JWT 토큰에서 사용자 정보 추출 - 큰 사용자 ID")
    void getUserFromToken_WithLargeUserId_Success() {
        // Given
        String token = "large.userid.jwt.token";
        Long expectedUserId = 999999999L;
        List<String> expectedRoles = Arrays.asList("ROLE_USER");

        given(jwtTokenProvider.getUserId(token)).willReturn(expectedUserId);
        given(jwtTokenProvider.getRoles(token)).willReturn(expectedRoles);

        // When
        UserResponseDTO result = userService.getUserFromToken(token);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(expectedUserId);
        assertThat(result.getRoles()).isEqualTo(expectedRoles);

        // Mock 호출 검증
        then(jwtTokenProvider).should().getUserId(token);
        then(jwtTokenProvider).should().getRoles(token);
    }

    @Test
    @DisplayName("JWT 토큰에서 사용자 정보 추출 - 모든 역할 조합")
    void getUserFromToken_WithAllRoles_Success() {
        // Given
        String token = "all.roles.jwt.token";
        Long expectedUserId = 7L;
        List<String> expectedRoles = Arrays.asList("ROLE_USER", "ROLE_TRAINER", "ROLE_ADMIN");

        given(jwtTokenProvider.getUserId(token)).willReturn(expectedUserId);
        given(jwtTokenProvider.getRoles(token)).willReturn(expectedRoles);

        // When
        UserResponseDTO result = userService.getUserFromToken(token);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(expectedUserId);
        assertThat(result.getRoles()).isEqualTo(expectedRoles);
        assertThat(result.getRoles()).hasSize(3);
        assertThat(result.getRoles()).containsExactly("ROLE_USER", "ROLE_TRAINER", "ROLE_ADMIN");

        // Mock 호출 검증
        then(jwtTokenProvider).should().getUserId(token);
        then(jwtTokenProvider).should().getRoles(token);
    }

    @Test
    @DisplayName("JWT 토큰에서 사용자 정보 추출 - 최소 사용자 ID")
    void getUserFromToken_WithMinimumUserId_Success() {
        // Given
        String token = "min.userid.jwt.token";
        Long expectedUserId = 1L;
        List<String> expectedRoles = Arrays.asList("ROLE_USER");

        given(jwtTokenProvider.getUserId(token)).willReturn(expectedUserId);
        given(jwtTokenProvider.getRoles(token)).willReturn(expectedRoles);

        // When
        UserResponseDTO result = userService.getUserFromToken(token);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getRoles()).isEqualTo(expectedRoles);

        // Mock 호출 검증
        then(jwtTokenProvider).should().getUserId(token);
        then(jwtTokenProvider).should().getRoles(token);
    }

    @Test
    @DisplayName("JWT 토큰에서 사용자 정보 추출 - 커스텀 역할")
    void getUserFromToken_WithCustomRoles_Success() {
        // Given
        String token = "custom.roles.jwt.token";
        Long expectedUserId = 8L;
        List<String> expectedRoles = Arrays.asList("ROLE_PREMIUM_USER", "ROLE_VIP");

        given(jwtTokenProvider.getUserId(token)).willReturn(expectedUserId);
        given(jwtTokenProvider.getRoles(token)).willReturn(expectedRoles);

        // When
        UserResponseDTO result = userService.getUserFromToken(token);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(expectedUserId);
        assertThat(result.getRoles()).isEqualTo(expectedRoles);
        assertThat(result.getRoles()).hasSize(2);
        assertThat(result.getRoles()).contains("ROLE_PREMIUM_USER", "ROLE_VIP");

        // Mock 호출 검증
        then(jwtTokenProvider).should().getUserId(token);
        then(jwtTokenProvider).should().getRoles(token);
    }

    @Test
    @DisplayName("UserResponseDTO 정적 팩토리 메서드 - toDTO 검증")
    void userResponseDTO_ToDTO_Success() {
        // Given
        Long userId = 100L;
        List<String> roles = Arrays.asList("ROLE_TEST");

        // When
        UserResponseDTO result = UserResponseDTO.toDTO(userId, roles);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getRoles()).isEqualTo(roles);
        assertThat(result.getUsername()).isNull(); // toDTO에서는 username을 설정하지 않음
        assertThat(result.getEmail()).isNull(); // toDTO에서는 email을 설정하지 않음
        assertThat(result.getCreatedAt()).isNull(); // toDTO에서는 createdAt을 설정하지 않음
        assertThat(result.getUpdatedAt()).isNull(); // toDTO에서는 updatedAt을 설정하지 않음
    }
}
