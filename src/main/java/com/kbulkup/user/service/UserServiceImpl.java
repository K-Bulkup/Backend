package com.kbulkup.user.service;

import com.kbulkup.common.security.JwtTokenProvider;
import com.kbulkup.user.dto.response.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtTokenProvider jwtTokenProvider;

    public UserResponseDTO getUserFromToken(String token) {
        Long userId = jwtTokenProvider.getUserId(token);
        List<String> roles = jwtTokenProvider.getRoles(token);

        return UserResponseDTO.toDTO(userId, roles);
    }
}
