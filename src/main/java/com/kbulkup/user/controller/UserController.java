package com.kbulkup.user.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.user.domain.User;
import com.kbulkup.user.dto.response.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common/users")
public class UserController {

    @GetMapping("/me")
    public CustomResponse<UserResponseDTO> getMyInfo(@AuthenticationPrincipal(expression = "user") User user) {
        UserResponseDTO dto = UserResponseDTO.toDTO(user.getUserId(), user.getRoles());
        return CustomResponse.success(ResponseCode.SUCCESS, dto);
    }
}
