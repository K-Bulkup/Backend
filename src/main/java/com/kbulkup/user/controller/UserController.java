package com.kbulkup.user.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.user.domain.User;
import com.kbulkup.user.dto.response.UserResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "User", description = "사용자 정보 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common/users")
public class UserController {

    @ApiOperation(value = "내 정보 조회", notes = "로그인한 사용자 정보(최소 식별자/권한)를 반환합니다.")
    @GetMapping("/me")
    public CustomResponse<UserResponseDTO> getMyInfo(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {
        UserResponseDTO dto = UserResponseDTO.toDTO(user.getUserId(), user.getRoles());
        return CustomResponse.success(ResponseCode.SUCCESS, dto);
    }
}
