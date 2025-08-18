package com.kbulkup.auth.dto.response;

import lombok.*;

// Swagger
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 로그아웃 성공 시 클라이언트에 반환될 응답 데이터를 담는 DTO
 */
@ApiModel(description = "로그아웃 응답")
@Getter
@Builder
public class LogoutResponseDTO {

    @ApiModelProperty(value = "메시지", example = "로그아웃 되었습니다.")
    private String message;
}
