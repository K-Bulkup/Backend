package com.kbulkup.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long userId;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> roles;

    // The static toDTO method is no longer strictly necessary for MyBatis mapping
    // but can be kept if used elsewhere for manual DTO creation.
    public static UserResponseDTO toDTO(Long userId, List<String> roles) {
        return UserResponseDTO
                .builder()
                .userId(userId)
                .roles(roles)
                .build();
    }
}