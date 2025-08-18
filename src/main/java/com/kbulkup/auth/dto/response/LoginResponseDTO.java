package com.kbulkup.auth.dto.response;

import com.kbulkup.user.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.util.Collections;
import java.util.List;

@ApiModel(description = "로그인 응답")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    @ApiModelProperty(value = "JWT 액세스 토큰")
    private String accessToken;

    @ApiModelProperty(value = "사용자 ID")
    private Long userId;

    @ApiModelProperty(value = "사용자 닉네임")
    private String username;

    @ApiModelProperty(value = "이메일")
    private String email;

    @ApiModelProperty(value = "역할 목록")
    private List<String> roles;

    @ApiModelProperty(value = "신규 사용자 여부")
    private boolean isNewUser;

    @ApiModelProperty(value = "로그인 타입", allowableValues = "LOCAL,KAKAO,NAVER")
    private String loginType;

    @ApiModelProperty(value = "소셜 제공자 ID")
    private String providerId;

    public static LoginResponseDTO toDTO(User user, String accessToken, String requestedRole, boolean isNewUser, String loginType, String providerId) {
        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(Collections.singletonList(requestedRole))
                .isNewUser(isNewUser)
                .loginType(loginType)
                .providerId(providerId)
                .build();
    }
}
