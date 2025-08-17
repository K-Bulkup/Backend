package com.kbulkup.admin.controller;

import com.kbulkup.auth.dto.request.LoginRequestDTO;
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.auth.service.LoginContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Admin Auth", description = "관리자 인증 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin") // 기본 경로를 /api/admin으로 설정
public class AdminController {

    private final LoginContext loginContext;

    // 관리자 로그인 엔드포인트
    @ApiOperation(
            value = "관리자 로그인",
            notes = "이메일/비밀번호로 로그인하고 JWT 토큰을 발급받습니다."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDTO> adminLogin(
            @ApiParam(value = "로그인 요청 바디", required = true)
            @RequestBody LoginRequestDTO dto) {
        // 관리자 로그인 로직 (일반 로그인과 동일한 LoginContext 사용)
        LoginResponseDTO responseDTO = loginContext.executeLogin(dto);
        return ResponseEntity.ok(responseDTO);
    }
}
