package com.kubulkup.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 로그아웃은 보통 JWT만 헤더로 보내므로, 별도의 요청 DTO 클래스가 필요 없는 경우가 많습니다.
// 그러나 만약을 위해 정의한다면 다음과 같이 비어있거나, 세션 정보 등을 포함할 수 있습니다.
@Data

public class LogoutRequestDTO {
    // 필요한 경우, 클라이언트에서 추가적인 로그아웃 정보를 보낼 수 있습니다.
    // private String sessionId;
}
