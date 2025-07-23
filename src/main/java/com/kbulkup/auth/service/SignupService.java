package com.kbulkup.auth.service;

import com.kbulkup.auth.dto.SignupRequestDTO;
import com.kbulkup.auth.dto.SignupResponseDTO;
import com.kbulkup.user.domain.User;
import com.kbulkup.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponseDTO signup(SignupRequestDTO signupRequestDTO) {
        String email = signupRequestDTO.getEmail();
        String loginType = signupRequestDTO.getLoginType(); // loginType을 요청에서 가져옴

        // (이메일, 로그인 타입) 쌍으로 중복 확인
        if (userMapper.findByEmailAndLoginType(email, loginType).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다. (동일한 로그인 타입)");
        }

        User user = User.builder()
                .username(signupRequestDTO.getName()) // username은 사용자 이름(닉네임)으로 사용
                .password(passwordEncoder.encode(signupRequestDTO.getPassword()))
                .email(email)
                .loginType(loginType)
                .providerId(signupRequestDTO.getProviderId()) // providerId 추가
                .birthdate(signupRequestDTO.getBirthdate()) // birthdate 추가
                .build();

        userMapper.saveUser(user);
        userMapper.saveUserRole(user.getUserId(), signupRequestDTO.getRole());

        return SignupResponseDTO.builder()
                .message("회원가입이 성공적으로 완료되었습니다.")
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}