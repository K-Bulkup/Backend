package com.kbulkup.auth.controller;

import com.kbulkup.auth.dto.request.LoginRequestDTO;
import com.kbulkup.auth.dto.request.SignupRequestDTO;
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.auth.dto.response.LogoutResponseDTO;
import com.kbulkup.auth.dto.response.SignupResponseDTO;
import com.kbulkup.auth.service.AuthService;
import com.kbulkup.auth.service.LogoutService;
import com.kbulkup.auth.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/common/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final LogoutService logoutService;
    private final SignupService signupService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO requestDTO) {
        LoginResponseDTO responseDTO = authService.login(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDTO> logout() {
        String message = logoutService.performLogout();

        LogoutResponseDTO response = LogoutResponseDTO.builder()
                .message(message)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody SignupRequestDTO requestDTO) {
        SignupResponseDTO responseDTO = signupService.signup(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
