package com.kbulkup.admin.controller;

import com.kbulkup.auth.dto.request.LoginRequestDTO;
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.auth.service.LoginContext;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Admin Auth", description = "관리자 인증 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final LoginContext loginContext;

    @ApiOperation(value = "관리자 로그인", notes = "이메일/비밀번호로 로그인하고 JWT 토큰을 발급받습니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패")
    })
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDTO> adminLogin(
            @ApiParam(value = "로그인 요청 바디", required = true)
            @RequestBody LoginRequestDTO dto) {
        LoginResponseDTO responseDTO = loginContext.executeLogin(dto);
        return ResponseEntity.ok(responseDTO);
    }
}
