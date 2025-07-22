package com.kbulkup.auth.service;

import com.kbulkup.auth.dto.SignupRequestDTO;
import com.kbulkup.auth.dto.SignupResponseDTO;
import com.kbulkup.user.domain.User;
import com.kbulkup.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponseDTO signup(SignupRequestDTO signupRequestDTO) {
        String email = signupRequestDTO.getEmail();
        String loginType = signupRequestDTO.getLoginType();
        String role = signupRequestDTO.getRole();

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

            return SignupResponseDTO.builder()
                    .message("기존 계정에 " + role + " 역할이 추가되었습니다.")
                    .userId(existingUser.getUserId())
                    .email(existingUser.getEmail())
                    .username(existingUser.getUsername())
                    .build();

        } else {
            // 사용자가 존재하지 않을 경우: 신규 회원가입 로직
            User newUser = User.builder()
                    .username(signupRequestDTO.getName())
                    .password(passwordEncoder.encode(signupRequestDTO.getPassword()))
                    .email(email)
                    .loginType(loginType)
                    .providerId(signupRequestDTO.getProviderId())
                    .birthdate(signupRequestDTO.getBirthdate())
                    .build();

            userMapper.saveUser(newUser);

            userMapper.saveUserRole(newUser.getUserId(), role);

            return SignupResponseDTO.builder()
                    .message("회원가입이 성공적으로 완료되었습니다.")
                    .userId(newUser.getUserId())
                    .email(newUser.getEmail())
                    .username(newUser.getUsername())
                    .build();
        }
    }
}
