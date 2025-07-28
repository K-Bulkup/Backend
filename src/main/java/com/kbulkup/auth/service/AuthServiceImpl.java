package com.kbulkup.auth.service;

import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.auth.dto.response.SignupResponseDTO;
import com.kbulkup.common.security.JwtTokenProvider;
import com.kbulkup.user.domain.User;
import com.kbulkup.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kbulkup.auth.exception.AuthException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.auth.domain.LoginType;
import com.kbulkup.user.domain.RoleType;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public LoginResponseDTO login(String loginType, String email, String password, String code, String role) {
        User user = userMapper.findByEmailAndLoginType(email, com.kbulkup.auth.domain.LoginType.LOCAL)
                .orElseThrow(() -> new AuthException(ResponseCode.INVALID_LOGIN_REQUEST));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthException(ResponseCode.INVALID_PASSWORD);
        }

        // 요청된 역할(role)이 사용자의 실제 역할 목록에 포함되어 있는지 확인
        String finalRoleToLogin;

        if (role == null) {
            // 역할이 지정되지 않은 경우, 사용자의 첫 번째 역할을 기본값으로 사용
            finalRoleToLogin = user.getRoles().stream()
                    .findFirst()
                    .orElseThrow(() -> new AuthException(ResponseCode.NO_ROLE_ASSIGNED));
        } else {
            // 역할이 지정된 경우, 해당 역할을 사용
            finalRoleToLogin = role;
            // 그리고 이 역할이 사용자의 실제 역할 목록에 포함되어 있는지 확인
            if (user.getRoles().stream().noneMatch(r -> r.equals(finalRoleToLogin))) {
                throw new AuthException(ResponseCode.INVALID_ROLE);
            }
        }

        // 요청된 역할만 포함하여 JWT 토큰 생성
        String accesstoken = jwtTokenProvider.createToken(user.getEmail(), user.getUserId(), Collections.singletonList(requestedRole));

        return LoginResponseDTO.toDTO(user, accesstoken, finalRoleToLogin);
    }

    @Override
    @Transactional
    public SignupResponseDTO signup(String userId, String password, String name, String email, String phone, String address, String role, String loginType, String providerId, String birthdate) {
        Optional<User> existingUserOptional = userMapper.findByEmailAndLoginType(email, loginType);

        if (existingUserOptional.isPresent()) {
            // 사용자가 이미 존재할 경우: 역할 추가 로직
            User existingUser = existingUserOptional.get();

            // 이미 해당 역할을 가지고 있는지 확인
            if (userMapper.existsUserRole(existingUser.getUserId(), role)) {
                throw new AuthException(ResponseCode.DUPLICATE_ROLE);
            }

            // 새로운 역할 추가
            userMapper.saveUserRole(existingUser.getUserId(), role);

            return SignupResponseDTO.toDTO(existingUser);
        } else {
            // 사용자가 존재하지 않을 경우: 신규 회원가입 로직
            User newUser = User.createUser(email, passwordEncoder.encode(password), name, loginType, providerId, birthdate);

            userMapper.saveUser(newUser);
            userMapper.saveUserRole(newUser.getUserId(), role);

            return SignupResponseDTO.toDTO(newUser);
        }
    }

    @Override
    public void logout() {
        // 현재 JWT(stateless) 방식에서는 서버에서 특별히 처리할 작업은 없습니다.
        // 클라이언트 측에서 토큰을 삭제하는 것이 핵심입니다.
        // 추후 토큰 블랙리스트와 같은 stateful 로직이 필요할 경우 여기에 구현합니다.
    }
}
