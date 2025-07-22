package com.kbulkup.auth.controller;

import com.kbulkup.auth.dto.SocialLoginResponseDTO;
import com.kbulkup.auth.service.NaverAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/naver")
public class NaverLoginController {

    private final NaverAuthService naverAuthService;

    @GetMapping("/callback")
    public ResponseEntity<SocialLoginResponseDTO> naverCallback(
            @RequestParam String code,
            @RequestParam String state) {
        SocialLoginResponseDTO response = naverAuthService.naverLogin(code, state);
        return ResponseEntity.ok(response);
    }
}
