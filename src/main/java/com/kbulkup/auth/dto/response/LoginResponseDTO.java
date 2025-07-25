package com.kbulkup.auth.dto.response;

import com.kbulkup.user.domain.RoleType;
import com.kbulkup.user.domain.User;
import lombok.*;
import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private String accessToken; // 최종 인증 JWT
    private Long userId;        // 사용자의 고유 ID
    private String nickname;    // 사용자의 닉네임
    private List<RoleType> roles; // 사용자가 가진 모든 역할 목록

    public static LoginResponseDTO toDTO(User user, String accessToken, RoleType requestedRole) {
        return LoginResponseDTO.builder().
                accessToken(accessToken).
                userId(user.getUserId()).
                nickname(user.getUsername()).
                roles(Collections.singletonList(requestedRole)).
                build();
    }
}
