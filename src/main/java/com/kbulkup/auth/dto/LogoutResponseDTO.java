package com.kbulkup.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data

@Builder
public class LogoutResponseDTO {
    private String message; // 예: "로그아웃 성공"
}
