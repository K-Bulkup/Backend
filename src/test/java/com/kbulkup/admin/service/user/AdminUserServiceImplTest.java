package com.kbulkup.admin.service.user;

import com.kbulkup.admin.mapper.AdminUserMapper;
import com.kbulkup.auth.dto.request.SignupRequestDTO;
import com.kbulkup.common.exception.AdminException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.user.dto.request.UserRequestDTO;
import com.kbulkup.user.dto.response.UserResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

/**
 * AdminUserServiceImpl 단위 테스트
 * 이 클래스는 관리자 사용자 서비스의 핵심 비즈니스 로직을 테스트합니다:
 * - 사용자 생성 및 역할 할당
 * - 사용자 조회 (단일/전체)
 * - 사용자 정보 수정
 * - 사용자 삭제
 * - 비밀번호 암호화 처리
 * - 예외 상황 처리
 */
@ExtendWith(MockitoExtension.class)
class AdminUserServiceImplTest {

    @Mock
    private AdminUserMapper adminUserMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminUserServiceImpl adminUserService;

    @Test
    @DisplayName("사용자 생성 - 트레이너 역할로 성공")
    void createUser_WithTrainerRole_Success() {
        // Given
        SignupRequestDTO dto = SignupRequestDTO.builder()
                .username("트레이너김")
                .email("trainer@example.com")
                .password("password123!")
                .phone("010-1234-5678")
                .address("서울시 강남구")
                .role("TRAINER")
                .loginType("LOCAL")
                .birthdate(LocalDateTime.of(1990, 1, 1, 0, 0))
                .build();

        String encodedPassword = "encoded_password123!";
        Long trainerRoleId = 2L;

        given(passwordEncoder.encode("password123!")).willReturn(encodedPassword);
        
        // userId 설정 시뮬레이션
        willAnswer(invocation -> {
            SignupRequestDTO signupDto = invocation.getArgument(0);
            ReflectionTestUtils.setField(signupDto, "userId", 1L);
            return null;
        }).given(adminUserMapper).insertUser(any(SignupRequestDTO.class));
        
        given(adminUserMapper.findRoleIdByRoleName("TRAINER")).willReturn(trainerRoleId);

        // When
        adminUserService.createUser(dto);

        // Then
        // 비밀번호 암호화 확인
        ArgumentCaptor<SignupRequestDTO> dtoCaptor = ArgumentCaptor.forClass(SignupRequestDTO.class);
        then(adminUserMapper).should().insertUser(dtoCaptor.capture());
        
        SignupRequestDTO capturedDto = dtoCaptor.getValue();
        assertThat(capturedDto.getPassword()).isEqualTo(encodedPassword);
        assertThat(capturedDto.getUsername()).isEqualTo("트레이너김");
        assertThat(capturedDto.getEmail()).isEqualTo("trainer@example.com");
        assertThat(capturedDto.getRole()).isEqualTo("TRAINER");

        // 역할 할당 확인
        then(adminUserMapper).should().findRoleIdByRoleName("TRAINER");
        then(adminUserMapper).should().insertUserRole(1L, trainerRoleId);

        // Mock 호출 검증
        then(passwordEncoder).should().encode("password123!");
    }

    @Test
    @DisplayName("사용자 생성 - 일반 회원 역할로 성공")
    void createUser_WithTraineeRole_Success() {
        // Given
        SignupRequestDTO dto = SignupRequestDTO.builder()
                .username("회원이")
                .email("trainee@example.com")
                .password("password456!")
                .role("TRAINEE")
                .loginType("LOCAL")
                .build();

        String encodedPassword = "encoded_password456!";
        Long traineeRoleId = 1L;

        given(passwordEncoder.encode("password456!")).willReturn(encodedPassword);
        
        willAnswer(invocation -> {
            SignupRequestDTO signupDto = invocation.getArgument(0);
            ReflectionTestUtils.setField(signupDto, "userId", 2L);
            return null;
        }).given(adminUserMapper).insertUser(any(SignupRequestDTO.class));
        
        given(adminUserMapper.findRoleIdByRoleName("TRAINEE")).willReturn(traineeRoleId);

        // When
        adminUserService.createUser(dto);

        // Then
        then(adminUserMapper).should().insertUser(any(SignupRequestDTO.class));
        then(adminUserMapper).should().findRoleIdByRoleName("TRAINEE");
        then(adminUserMapper).should().insertUserRole(2L, traineeRoleId);
    }

    @Test
    @DisplayName("사용자 생성 - 존재하지 않는 역할로 기본 역할 할당")
    void createUser_WithInvalidRole_AssignsDefaultRole() {
        // Given
        SignupRequestDTO dto = SignupRequestDTO.builder()
                .username("사용자")
                .email("user@example.com")
                .password("password789!")
                .role("INVALID_ROLE")
                .loginType("LOCAL")
                .build();

        String encodedPassword = "encoded_password789!";
        Long defaultRoleId = 1L;

        given(passwordEncoder.encode("password789!")).willReturn(encodedPassword);
        
        willAnswer(invocation -> {
            SignupRequestDTO signupDto = invocation.getArgument(0);
            ReflectionTestUtils.setField(signupDto, "userId", 3L);
            return null;
        }).given(adminUserMapper).insertUser(any(SignupRequestDTO.class));
        
        given(adminUserMapper.findRoleIdByRoleName("INVALID_ROLE")).willReturn(null);
        given(adminUserMapper.findRoleIdByRoleName("TRAINEE")).willReturn(defaultRoleId);

        // When
        adminUserService.createUser(dto);

        // Then
        then(adminUserMapper).should().findRoleIdByRoleName("INVALID_ROLE");
        then(adminUserMapper).should().findRoleIdByRoleName("TRAINEE");
        then(adminUserMapper).should().insertUserRole(3L, defaultRoleId);
    }

    @Test
    @DisplayName("사용자 생성 - 기본 역할도 없는 경우 예외 발생")
    void createUser_WithNoDefaultRole_ThrowsException() {
        // Given
        SignupRequestDTO dto = SignupRequestDTO.builder()
                .username("사용자")
                .email("user@example.com")
                .password("password789!")
                .role("INVALID_ROLE")
                .loginType("LOCAL")
                .build();

        String encodedPassword = "encoded_password789!";

        given(passwordEncoder.encode("password789!")).willReturn(encodedPassword);
        
        willAnswer(invocation -> {
            SignupRequestDTO signupDto = invocation.getArgument(0);
            ReflectionTestUtils.setField(signupDto, "userId", 4L);
            return null;
        }).given(adminUserMapper).insertUser(any(SignupRequestDTO.class));
        
        given(adminUserMapper.findRoleIdByRoleName("INVALID_ROLE")).willReturn(null);
        given(adminUserMapper.findRoleIdByRoleName("TRAINEE")).willReturn(null);

        // When & Then
        assertThatThrownBy(() -> adminUserService.createUser(dto))
                .isInstanceOf(AdminException.class)
                .hasFieldOrPropertyWithValue("responseCode", ResponseCode.ADMIN_USER_ROLE_MISSING);

        // Mock 호출 검증
        then(adminUserMapper).should().insertUser(any(SignupRequestDTO.class));
        then(adminUserMapper).should().findRoleIdByRoleName("INVALID_ROLE");
        then(adminUserMapper).should().findRoleIdByRoleName("TRAINEE");
        then(adminUserMapper).should(never()).insertUserRole(anyLong(), anyLong());
    }

    @Test
    @DisplayName("사용자 ID로 조회 - 성공")
    void getUserById_Success() {
        // Given
        Long userId = 1L;
        UserResponseDTO expectedUser = UserResponseDTO.builder()
                .userId(userId)
                .username("조회사용자")
                .email("user@example.com")
                .roles(Arrays.asList("ROLE_TRAINEE"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(adminUserMapper.findUserById(userId)).willReturn(expectedUser);

        // When
        UserResponseDTO result = adminUserService.getUserById(userId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getUsername()).isEqualTo("조회사용자");
        assertThat(result.getEmail()).isEqualTo("user@example.com");
        assertThat(result.getRoles()).contains("ROLE_TRAINEE");

        // Mock 호출 검증
        then(adminUserMapper).should().findUserById(userId);
    }

    @Test
    @DisplayName("사용자 ID로 조회 - 존재하지 않는 사용자")
    void getUserById_UserNotFound_ReturnsNull() {
        // Given
        Long userId = 999L;
        
        given(adminUserMapper.findUserById(userId)).willReturn(null);

        // When
        UserResponseDTO result = adminUserService.getUserById(userId);

        // Then
        assertThat(result).isNull();

        // Mock 호출 검증
        then(adminUserMapper).should().findUserById(userId);
    }

    @Test
    @DisplayName("전체 사용자 조회 - 다수의 사용자")
    void getAllUsers_WithMultipleUsers_Success() {
        // Given
        List<UserResponseDTO> expectedUsers = Arrays.asList(
                UserResponseDTO.builder()
                        .userId(1L)
                        .username("사용자1")
                        .email("user1@example.com")
                        .roles(Arrays.asList("ROLE_TRAINEE"))
                        .build(),
                UserResponseDTO.builder()
                        .userId(2L)
                        .username("트레이너1")
                        .email("trainer1@example.com")
                        .roles(Arrays.asList("ROLE_TRAINER"))
                        .build(),
                UserResponseDTO.builder()
                        .userId(3L)
                        .username("관리자1")
                        .email("admin1@example.com")
                        .roles(Arrays.asList("ROLE_ADMIN"))
                        .build()
        );

        given(adminUserMapper.findAllUsers()).willReturn(expectedUsers);

        // When
        List<UserResponseDTO> result = adminUserService.getAllUsers();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
        
        assertThat(result.get(0).getUsername()).isEqualTo("사용자1");
        assertThat(result.get(0).getRoles()).contains("ROLE_TRAINEE");
        
        assertThat(result.get(1).getUsername()).isEqualTo("트레이너1");
        assertThat(result.get(1).getRoles()).contains("ROLE_TRAINER");
        
        assertThat(result.get(2).getUsername()).isEqualTo("관리자1");
        assertThat(result.get(2).getRoles()).contains("ROLE_ADMIN");

        // Mock 호출 검증
        then(adminUserMapper).should().findAllUsers();
    }

    @Test
    @DisplayName("전체 사용자 조회 - 빈 리스트")
    void getAllUsers_EmptyList_Success() {
        // Given
        List<UserResponseDTO> emptyList = Collections.emptyList();
        
        given(adminUserMapper.findAllUsers()).willReturn(emptyList);

        // When
        List<UserResponseDTO> result = adminUserService.getAllUsers();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        // Mock 호출 검증
        then(adminUserMapper).should().findAllUsers();
    }

    @Test
    @DisplayName("사용자 정보 수정 - 성공")
    void updateUser_Success() {
        // Given
        Long userId = 1L;
        UserRequestDTO updateDto = new UserRequestDTO();

        // When
        adminUserService.updateUser(userId, updateDto);

        // Then
        // Mock 호출 검증
        then(adminUserMapper).should().updateUser(userId, updateDto);
    }

    @Test
    @DisplayName("사용자 삭제 - 성공")
    void deleteUser_Success() {
        // Given
        Long userId = 1L;

        // When
        adminUserService.deleteUser(userId);

        // Then
        // Mock 호출 검증
        then(adminUserMapper).should().deleteUser(userId);
    }

    @Test
    @DisplayName("사용자 생성 - 소셜 로그인 사용자")
    void createUser_SocialLoginUser_Success() {
        // Given
        SignupRequestDTO dto = SignupRequestDTO.builder()
                .username("카카오사용자")
                .email("kakao@example.com")
                .password("password123!")
                .role("TRAINEE")
                .loginType("KAKAO")
                .providerId("kakao_12345")
                .build();

        String encodedPassword = "encoded_social_password";
        Long traineeRoleId = 1L;

        given(passwordEncoder.encode("password123!")).willReturn(encodedPassword);
        
        willAnswer(invocation -> {
            SignupRequestDTO signupDto = invocation.getArgument(0);
            ReflectionTestUtils.setField(signupDto, "userId", 5L);
            return null;
        }).given(adminUserMapper).insertUser(any(SignupRequestDTO.class));
        
        given(adminUserMapper.findRoleIdByRoleName("TRAINEE")).willReturn(traineeRoleId);

        // When
        adminUserService.createUser(dto);

        // Then
        ArgumentCaptor<SignupRequestDTO> dtoCaptor = ArgumentCaptor.forClass(SignupRequestDTO.class);
        then(adminUserMapper).should().insertUser(dtoCaptor.capture());
        
        SignupRequestDTO capturedDto = dtoCaptor.getValue();
        assertThat(capturedDto.getLoginType()).isEqualTo("KAKAO");
        assertThat(capturedDto.getProviderId()).isEqualTo("kakao_12345");
        assertThat(capturedDto.getPassword()).isEqualTo(encodedPassword);

        then(adminUserMapper).should().insertUserRole(5L, traineeRoleId);
    }
}
