package com.kbulkup.auth.controller;

import com.kbulkup.auth.dto.request.LoginRequestDTO;
import com.kbulkup.auth.dto.request.SignupRequestDTO;
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.auth.dto.response.LogoutResponseDTO;
import com.kbulkup.auth.dto.response.SignupResponseDTO;
import com.kbulkup.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common/auth/")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        LoginResponseDTO responseDTO = authService.login(dto.getLoginType(), dto.getEmail(), dto.getPassword(), dto.getCode(),  dto.getRole());
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody SignupRequestDTO dto) {
        SignupResponseDTO responseDTO = authService.signup(dto.getUserId(), dto.getPassword(), dto.getName(), dto.getEmail(), dto.getPhone(), dto.getAddress(), dto.getRole(), dto.getLoginType(), dto.getProviderId(), dto.getBirthdate());
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authService.logout();
        return ResponseEntity.ok("로그아웃");
    }
}
