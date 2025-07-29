package com.kbulkup.auth.dto.response;

import com.kbulkup.user.domain.User;
import lombok.*;
import java.util.Collections;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class LoginResponseDTO {
    private String accessToken; // 최종 인증 JWT
    private Long userId;        // 사용자의 고유 ID
    private String username;    // 사용자의 닉네임
    private String email;       // 사용자의 이메일
    private List<String> roles; // 사용자가 가진 모든 역할 목록
    private boolean isNewUser;

    public static LoginResponseDTO toDTO(User user, String accessToken, List<String> roles, boolean isNewUser) {
        return LoginResponseDTO.builder().
                accessToken(accessToken).
                userId(user.getUserId()).
                username(user.getUsername()).
                email(user.getEmail()).
                roles(roles).
                isNewUser(isNewUser).
                build();
    }

    public static LoginResponseDTO toDTO(User user, String accessToken, String requestedRole, boolean isNewUser) {
        return LoginResponseDTO.builder().
                accessToken(accessToken).
                userId(user.getUserId()).
                username(user.getUsername()).
                email(user.getEmail()).
                roles(Collections.singletonList(requestedRole)).
                isNewUser(isNewUser).
                build();
    }
}
