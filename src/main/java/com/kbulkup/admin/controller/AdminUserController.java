package com.kbulkup.admin.controller;

import com.kbulkup.admin.service.user.AdminUserService;
import com.kbulkup.auth.dto.request.SignupRequestDTO;
import com.kbulkup.user.dto.request.UserRequestDTO;
import com.kbulkup.user.dto.response.UserResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Admin User", description = "관리자 사용자 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @ApiOperation(value = "사용자 생성", notes = "관리자가 신규 사용자를 생성합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "요청 값 오류"),
            @ApiResponse(code = 409, message = "중복(이미 존재)")
    })
    @PostMapping
    public ResponseEntity<Void> createUser(
            @ApiParam(value = "회원가입 요청 바디", required = true)
            @RequestBody SignupRequestDTO dto) {
        adminUserService.createUser(dto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "사용자 단건 조회", notes = "userId로 사용자 상세를 조회합니다.")
    @ApiImplicitParam(name = "userId", value = "사용자 ID",
            required = true, dataType = "long", paramType = "path", example = "1001")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "대상 없음")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
        UserResponseDTO user = adminUserService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "사용자 목록", notes = "전체 사용자 목록을 반환합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = adminUserService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @ApiOperation(value = "사용자 수정", notes = "userId로 사용자를 수정합니다.")
    @ApiImplicitParam(name = "userId", value = "사용자 ID",
            required = true, dataType = "long", paramType = "path", example = "1001")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(
            @PathVariable Long userId,
            @ApiParam(value = "수정 요청 바디", required = true)
            @RequestBody UserRequestDTO dto) {
        adminUserService.updateUser(userId, dto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "사용자 삭제", notes = "userId로 사용자를 삭제합니다.")
    @ApiImplicitParam(name = "userId", value = "사용자 ID",
            required = true, dataType = "long", paramType = "path", example = "1001")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        adminUserService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
