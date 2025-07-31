package com.kbulkup.auth.service;

import com.kbulkup.auth.dto.request.SocialSignUpRequestDTO;
import com.kbulkup.auth.dto.request.SignupRequestDTO;
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.auth.dto.response.SignupResponseDTO;
import com.kbulkup.common.exception.AuthException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.common.security.JwtTokenProvider;
import com.kbulkup.user.domain.User;
import com.kbulkup.user.mapper.UserMapper;
import com.kbulkup.auth.domain.Role;
import com.kbulkup.user.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public SignupResponseDTO signup(SignupRequestDTO dto) {
        // 이메일 중복 확인
        Optional<User> existingUser = userMapper.findByEmail(dto.getEmail());

        if (existingUser.isPresent()) {
            // 이미 역할이 존재하는지 확인
            if (userMapper.existsUserRole(existingUser.get().getUserId(), dto.getRole())) {
                throw new AuthException(ResponseCode.AUTH_INVALID_ROLE);
            } else {
                // 역할 추가
                userMapper.saveUserRole(existingUser.get().getUserId(), dto.getRole());
                return SignupResponseDTO.builder()
                        .success(true)
                        .userId(existingUser.get().getUserId())
                        .email(existingUser.get().getEmail())
                        .username(existingUser.get().getUsername())
                        .loginType(existingUser.get().getLoginType())
                        .build();
            }
        }

        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(dto.getPassword());

        // User 객체 생성
        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(encryptedPassword)
                .loginType("LOCAL")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // DB에 사용자 정보 저장
        userMapper.saveUser(user);

        // 로컬 로그인 사용자는 회원가입 시 역할을 즉시 부여
        userMapper.saveUserRole(user.getUserId(), dto.getRole());

        // 사용자의 역할이 트레이너일 경우, 프로필 생성을 위한 이벤트 발행
        if (dto.getRole().equals(Role.TRAINER)) {
            eventPublisher.publishEvent(new UserRegisteredEvent(this, user));
        }

        return SignupResponseDTO.builder()
                .success(true)
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .loginType(user.getLoginType())
                .build();
    }

    @Override
    public LoginResponseDTO socialSignUp(SocialSignUpRequestDTO dto) {
        // 임시 토큰에서 사용자 ID 추출 및 유효성 검증
        Long userId = jwtTokenProvider.getUserIdFromTempToken(dto.getTempAccessToken());
        if (userId == null) {
            throw new AuthException(ResponseCode.AUTH_VALIDATION_ERROR);
        }

        // DB에서 사용자 정보 조회
        User user = userMapper.findById(userId)
                .orElseThrow(() -> new AuthException(ResponseCode.AUTH_USER_NOT_FOUND));

        // 사용자가 선택한 역할을 이미 가지고 있는지 확인하고, 없는 경우에만 새로 저장
        if (!userMapper.existsUserRole(user.getUserId(), dto.getRole())) {
            userMapper.saveUserRole(user.getUserId(), dto.getRole());
        }

        // 사용자의 역할이 트레이너일 경우, 프로필 생성을 위한 이벤트 발행
        if (dto.getRole().equals(Role.TRAINER)) {
            eventPublisher.publishEvent(new UserRegisteredEvent(this, user));
        }

        // 최종 액세스 토큰은 *선택된 역할 하나*에 대해서만 발급
        String accessToken = jwtTokenProvider.createAccessToken(user.getUserId(), java.util.Collections.singletonList(dto.getRole()));

        // 응답 DTO에는 *선택된 역할 하나*만 담아서 반환
        return LoginResponseDTO.toDTO(user, accessToken, dto.getRole(), false, user.getLoginType(), user.getProviderId());
    }

    @Override
    public void logout() {
        // JWT는 서버에 상태를 저장하지 않는 무상태(stateless) 방식입니다.
        // 따라서 백엔드에서는 별다른 처리 없이, 클라이언트에서 토큰을 삭제하면 로그아웃됩니다.
    }
}