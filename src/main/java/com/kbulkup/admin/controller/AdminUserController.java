package com.kbulkup.admin.controller;

import com.kbulkup.admin.service.user.AdminUserService;
import com.kbulkup.auth.dto.request.SignupRequestDTO;
import com.kbulkup.user.dto.request.UserRequestDTO;
import com.kbulkup.user.dto.response.UserResponseDTO;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Admin User", description = "관리자 사용자 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @ApiOperation(value = "사용자 생성", notes = "관리자가 신규 사용자를 생성합니다.")
    @PostMapping
    public ResponseEntity<Void> createUser(
            @ApiParam(value = "회원가입 요청 바디", required = true)
            @RequestBody SignupRequestDTO dto) {
        adminUserService.createUser(dto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "사용자 단건 조회", notes = "userId로 사용자 상세를 조회합니다.")
    @ApiImplicitParam(name = "userId", value = "사용자 ID", required = true,
            dataType = "long", paramType = "path")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(adminUserService.getUserById(userId));
    }

    @ApiOperation(value = "사용자 목록", notes = "전체 사용자 목록을 반환합니다.")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(adminUserService.getAllUsers());
    }

    @ApiOperation(value = "사용자 수정", notes = "userId로 사용자를 수정합니다.")
    @ApiImplicitParam(name = "userId", value = "사용자 ID", required = true,
            dataType = "long", paramType = "path")
    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(
            @PathVariable Long userId,
            @ApiParam(value = "수정 요청 바디", required = true)
            @RequestBody UserRequestDTO dto) {
        adminUserService.updateUser(userId, dto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "사용자 삭제", notes = "userId로 사용자를 삭제합니다.")
    @ApiImplicitParam(name = "userId", value = "사용자 ID", required = true,
            dataType = "long", paramType = "path")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        adminUserService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
