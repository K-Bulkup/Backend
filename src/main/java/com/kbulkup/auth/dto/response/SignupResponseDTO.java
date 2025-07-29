package com.kbulkup.auth.dto.response;

import com.kbulkup.user.domain.User;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class SignupResponseDTO {

    private boolean success;
    private Long userId;
    private String email;
    private String username;
    private String loginType;
    private String message;

    public static SignupResponseDTO toDTO(User user) {
        return SignupResponseDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .loginType(user.getLoginType())
                .build();
    }

}