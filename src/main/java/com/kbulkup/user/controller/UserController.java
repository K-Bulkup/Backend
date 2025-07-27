package com.kbulkup.user.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.common.security.JwtTokenProvider;
import com.kbulkup.user.dto.response.UserResponseDTO;
import com.kbulkup.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/me")
    public CustomResponse<UserResponseDTO> getMyInfo(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        return CustomResponse.success(ResponseCode.SUCCESS, userService.getUserFromToken(token));
    }
}
