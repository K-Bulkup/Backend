package com.kbulkup.auth.dto.request;

import lombok.*;

// Swagger
import io.swagger.annotations.ApiModel;

@ApiModel(description = "로그아웃 요청")
@Getter
@Builder
@NoArgsConstructor
public class LogoutRequestDTO {
    // 필요한 경우, 클라이언트에서 추가적인 로그아웃 정보를 보낼 수 있습니다.
    // private String sessionId;
}
