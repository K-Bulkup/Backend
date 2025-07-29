package com.kbulkup.auth.service;

import com.kbulkup.auth.dto.request.SocialSignUpRequestDTO;
import com.kbulkup.auth.dto.request.SignupRequestDTO;
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.auth.dto.response.SignupResponseDTO;
import com.kbulkup.common.exception.BaseException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.common.security.JwtTokenProvider;
import com.kbulkup.user.domain.User;
import com.kbulkup.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
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

    @Override
    public SignupResponseDTO signup(SignupRequestDTO dto) {
        // 이메일 중복 확인
        Optional<User> existingUser = userMapper.findByEmail(dto.getEmail());

        if (existingUser.isPresent()) {

            // 이미 역할이 존재하는지 확인
            if (userMapper.existsUserRole(dto.getUserId(), dto.getRole())) {
                return SignupResponseDTO.builder()
                        .success(true)
                        .userId(dto.getUserId())
                        .email(dto.getEmail())
                        .username(dto.getUsername())
                        .loginType(dto.getLoginType())
                        .message("이미 해당 역할로 가입된 사용자입니다.")
                        .build();
            } else {
                // 역할 추가
                userMapper.saveUserRole(dto.getUserId(), dto.getRole());
                return SignupResponseDTO.builder()
                        .success(true)
                        .userId(dto.getUserId())
                        .email(dto.getEmail())
                        .username(dto.getUsername())
                        .loginType(dto.getLoginType())
                        .message("역할이 성공적으로 추가되었습니다.")
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

        return SignupResponseDTO.builder()
                .success(true)
                .userId(dto.getUserId())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .loginType(dto.getLoginType())
                .message("회원가입 및 역할 부여 성공.")
                .build();
    }

    @Override
    public LoginResponseDTO socialSignUp(SocialSignUpRequestDTO dto) {
        // 임시 토큰에서 사용자 ID 추출 및 유효성 검증
        Long userId = jwtTokenProvider.getUserIdFromTempToken(dto.getTempAccessToken());
        if (userId == null) {
            throw new BaseException(ResponseCode.VALIDATION_ERROR);
        }

        // DB에서 사용자 정보 조회
        User user = userMapper.findById(userId)
                .orElseThrow(() -> new BaseException(ResponseCode.USER_NOT_FOUND));

        // 이미 역할이 존재하는지 확인
        if (userMapper.existsUserRole(user.getUserId(), dto.getRole())) {
            throw new BaseException(ResponseCode.DUPLICATE_ROLE);
        }

        // 사용자가 선택한 새로운 역할 저장
        userMapper.saveUserRole(user.getUserId(), dto.getRole());

        // 역할이 추가된 최신 사용자 정보 다시 로드
        User updatedUser = userMapper.findById(userId)
                .orElseThrow(() -> new BaseException(ResponseCode.USER_NOT_FOUND));

        // 최종 액세스 토큰 발급
        String accessToken = jwtTokenProvider.createAccessToken(updatedUser.getUserId(), updatedUser.getRoles());

        return LoginResponseDTO.toDTO(updatedUser, accessToken, updatedUser.getRoles(), false, updatedUser.getLoginType(), updatedUser.getProviderId());
    }

    @Override
    public void logout() {
        // JWT는 서버에 상태를 저장하지 않는 무상태(stateless) 방식입니다.
        // 따라서 백엔드에서는 별다른 처리 없이, 클라이언트에서 토큰을 삭제하면 로그아웃됩니다.
    }
}