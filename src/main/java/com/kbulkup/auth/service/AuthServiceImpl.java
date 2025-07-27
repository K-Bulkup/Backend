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
        User user = userMapper.findByEmailAndLoginType(email, "LOCAL")
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일이거나 소셜 로그인 계정입니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        // 요청된 역할(role)이 사용자의 실제 역할 목록에 포함되어 있는지 확인
        String requestedRole = role;
        if (requestedRole == null || user.getRoles().stream().noneMatch(r -> r.equalsIgnoreCase(requestedRole))) {
            throw new IllegalArgumentException("요청한 역할(" + requestedRole + ")로 로그인할 수 없습니다. 사용자의 역할 목록에 없습니다.");
        }

        // 요청된 역할만 포함하여 JWT 토큰 생성
        String accesstoken = jwtTokenProvider.createToken(user.getEmail(), user.getUserId(), Collections.singletonList(requestedRole));

        return LoginResponseDTO.toDTO(user, accesstoken, role);
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
                throw new IllegalArgumentException("이미 해당 역할로 가입된 사용자입니다.");
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
