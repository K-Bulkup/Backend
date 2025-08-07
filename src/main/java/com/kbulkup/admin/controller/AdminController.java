package com.kbulkup.admin.controller;

import com.kbulkup.auth.dto.request.LoginRequestDTO;
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.auth.service.LoginContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin") // 기본 경로를 /api/admin으로 설정
public class AdminController {

    private final LoginContext loginContext;

    // 관리자 로그인 엔드포인트
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDTO> adminLogin(@RequestBody LoginRequestDTO dto) {
        // 관리자 로그인 로직 (일반 로그인과 동일한 LoginContext 사용)
        LoginResponseDTO responseDTO = loginContext.executeLogin(dto);
        return ResponseEntity.ok(responseDTO);
    }
}