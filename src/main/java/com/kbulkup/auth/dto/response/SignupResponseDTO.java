package com.kbulkup.auth.dto.response;

import com.kbulkup.user.domain.User;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupResponseDTO {

    private Long userId;
    private String email;
    private String username;

    public static SignupResponseDTO toDTO(User user) {
        return SignupResponseDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

}