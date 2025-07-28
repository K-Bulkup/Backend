package com.kbulkup.user.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserResponseDTO {

    private Long userId;
    private List<String> roles;

    public static UserResponseDTO toDTO(Long userId, List<String> roles) {
        return UserResponseDTO
                .builder()
                .userId(userId)
                .roles(roles)
                .build();
    }
}
