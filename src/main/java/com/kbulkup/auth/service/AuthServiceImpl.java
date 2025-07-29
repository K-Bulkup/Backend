package com.kbulkup.auth.service;

import com.kbulkup.auth.dto.request.LoginRequestDTO;
import com.kbulkup.auth.dto.request.SignupRequestDTO;
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
    public LoginResponseDTO login(LoginRequestDTO dto) {
        User user = userMapper.findByEmailAndLoginType(dto.getEmail(), LoginType.LOCAL)
                .orElseThrow(() -> new AuthException(ResponseCode.INVALID_LOGIN_REQUEST));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new AuthException(ResponseCode.INVALID_PASSWORD);
        }

        String requestedRole;

        if (dto.getRole() == null) {
            requestedRole = user.getRoles().stream()
                    .findFirst()
                    .orElseThrow(() -> new AuthException(ResponseCode.NO_ROLE_ASSIGNED));
        } else {
            requestedRole = dto.getRole();
            if (user.getRoles().stream().noneMatch(r -> r.equals(requestedRole))) {
                throw new AuthException(ResponseCode.INVALID_ROLE);
            }
        }

        // 요청된 역할만 포함하여 JWT 토큰 생성
        String accessToken = jwtTokenProvider.createToken(user.getEmail(), user.getUserId(), Collections.singletonList(requestedRole));

        return LoginResponseDTO.toDTO(user, accessToken, requestedRole);
    }

    @Override
    @Transactional
    public SignupResponseDTO signup(SignupRequestDTO dto) {
        Optional<User> existingUserOptional = userMapper.findByEmailAndLoginType(dto.getEmail(), dto.getLoginType());

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            if (userMapper.existsUserRole(existingUser.getUserId(), dto.getRole())) {
                throw new AuthException(ResponseCode.DUPLICATE_ROLE);
            }

            userMapper.saveUserRole(existingUser.getUserId(), dto.getRole());
            return SignupResponseDTO.toDTO(existingUser);
        } else {
            User newUser = User.createUser(
                    dto.getEmail(),
                    passwordEncoder.encode(dto.getPassword()),
                    dto.getUsername(),
                    dto.getLoginType(),
                    dto.getProviderId(),
                    dto.getBirthdate()
            );

            userMapper.saveUser(newUser);
            userMapper.saveUserRole(newUser.getUserId(), dto.getRole());

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
