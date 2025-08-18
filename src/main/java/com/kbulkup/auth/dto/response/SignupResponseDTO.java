package com.kbulkup.auth.dto.response;

import com.kbulkup.user.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "회원가입 응답")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponseDTO {

    @ApiModelProperty(value = "성공 여부")
    private boolean success;

    @ApiModelProperty(value = "사용자 ID")
    private Long userId;

    @ApiModelProperty(value = "이메일")
    private String email;

    @ApiModelProperty(value = "사용자 이름")
    private String username;

    @ApiModelProperty(value = "로그인 타입", allowableValues = "LOCAL,KAKAO,NAVER")
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
