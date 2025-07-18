package com.kubulkup.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@Builder
public class LogoutResponseDTO {
    private String message; // 예: "로그아웃 성공"
}
