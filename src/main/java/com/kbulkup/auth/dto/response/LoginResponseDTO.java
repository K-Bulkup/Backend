package com.kbulkup.auth.dto.response;

import com.kbulkup.user.domain.User;
import lombok.*;
import java.util.Collections;
import java.util.List;

// Swagger
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "로그인 응답")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    @ApiModelProperty(value = "JWT 액세스 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6...")
    private String accessToken; // 최종 인증 JWT

    @ApiModelProperty(value = "사용자 ID", example = "1001")
    private Long userId;        // 사용자의 고유 ID

    @ApiModelProperty(value = "사용자 닉네임", example = "kbulkup_user")
    private String username;    // 사용자의 닉네임

    @ApiModelProperty(value = "이메일", example = "user@example.com")
    private String email;       // 사용자의 이메일

    @ApiModelProperty(value = "역할 목록", example = "[\"TRAINEE\"]")
    private List<String> roles; // 사용자가 가진 모든 역할 목록

    @ApiModelProperty(value = "신규 사용자 여부", example = "false")
    private boolean isNewUser;

    @ApiModelProperty(value = "로그인 타입", allowableValues = "LOCAL,KAKAO,NAVER", example = "LOCAL")
    private String loginType; // loginType 필드 추가

    @ApiModelProperty(value = "소셜 제공자 ID", example = "1234567890")
    private String providerId; // providerId 필드 추가

    public static LoginResponseDTO toDTO(User user, String accessToken, String requestedRole, boolean isNewUser, String loginType, String providerId) {
        return LoginResponseDTO.builder().
                accessToken(accessToken).
                userId(user.getUserId()).
                username(user.getUsername()).
                email(user.getEmail()).
                roles(Collections.singletonList(requestedRole)).
                isNewUser(isNewUser).
                loginType(loginType).
                providerId(providerId).
                build();
    }
}
