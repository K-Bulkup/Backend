package com.kbulkup.user.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "사용자 기본 정보 응답")
public class UserResponseDTO {

    @ApiModelProperty(value = "사용자 ID")
    private Long userId;

    @ApiModelProperty(value = "사용자명")
    private String username;

    @ApiModelProperty(value = "이메일(보안/정책에 따라 비노출 가능)")
    private String email;

    @ApiModelProperty(value = "생성 시각")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "수정 시각")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "역할 목록")
    private List<String> roles;

    public static UserResponseDTO toDTO(Long userId, List<String> roles) {
        return UserResponseDTO
                .builder()
                .userId(userId)
                .roles(roles)
                .build();
    }
}
