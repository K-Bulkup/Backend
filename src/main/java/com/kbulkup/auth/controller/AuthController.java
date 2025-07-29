package com.kbulkup.auth.controller;

import com.kbulkup.auth.dto.request.LoginRequestDTO;
import com.kbulkup.auth.dto.request.SignupRequestDTO;
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.auth.dto.response.LogoutResponseDTO;
import com.kbulkup.auth.dto.response.SignupResponseDTO;
import com.kbulkup.auth.service.AuthService;
import com.kbulkup.auth.service.LoginContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common/auth")
public class AuthController {

    private final AuthService authService;
    private final LoginContext loginContext; // LoginContext 의존성 추가

    // 1. 로그인 엔드포인트: LoginContext로 위임
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        // 모든 로그인 로직은 LoginContext를 통해 처리
        LoginResponseDTO responseDTO = loginContext.executeLogin(dto);
        return ResponseEntity.ok(responseDTO);
    }

    // 2. 회원가입 엔드포인트: AuthService로 위임 (기존과 동일)
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody SignupRequestDTO dto) {
        SignupResponseDTO responseDTO = authService.signup(dto);
        return ResponseEntity.ok(responseDTO);
    }

    // 3. 로그아웃 엔드포인트: AuthService로 위임 (기존과 동일)
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authService.logout();
        return ResponseEntity.ok("로그아웃");
    }
}