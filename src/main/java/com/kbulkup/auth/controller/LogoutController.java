package com.kbulkup.auth.controller;

import com.kbulkup.auth.dto.LogoutResponseDTO;
import com.kbulkup.auth.service.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 로그아웃 요청을 처리하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class LogoutController {

    private final LogoutService logoutService;

    /**
     * 로그아웃을 처리합니다.
     * 클라이언트는 이 API 호출 후 저장된 토큰을 삭제해야 합니다.
     * @return 로그아웃 성공 메시지
     */
    @PostMapping("/api/common/auth/logout")
    public ResponseEntity<LogoutResponseDTO> logout() {
        String message = logoutService.performLogout();

        LogoutResponseDTO response = LogoutResponseDTO.builder()
                .message(message)
                .build();

        return ResponseEntity.ok(response);
    }
}
