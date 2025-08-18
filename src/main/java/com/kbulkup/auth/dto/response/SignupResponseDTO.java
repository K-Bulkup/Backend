package com.kbulkup.auth.dto.response;

import com.kbulkup.user.domain.User;
import lombok.*;

// Swagger
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "회원가입 응답")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponseDTO {

    @ApiModelProperty(value = "성공 여부", example = "true")
    private boolean success;

    @ApiModelProperty(value = "사용자 ID", example = "1001")
    private Long userId;

    @ApiModelProperty(value = "이메일", example = "user@example.com")
    private String email;

    @ApiModelProperty(value = "사용자 이름", example = "홍길동")
    private String username;

    @ApiModelProperty(value = "로그인 타입", allowableValues = "LOCAL,KAKAO,NAVER", example = "LOCAL")
    private String loginType;

    public static SignupResponseDTO toDTO(User user) {
        return SignupResponseDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .loginType(user.getLoginType())
                .build();
    }
}
