package com.kbulkup.auth.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 로그아웃 성공 시 클라이언트에 반환될 응답 데이터를 담는 DTO
 */
@Data
@Builder
public class LogoutResponseDTO {
    private String message;
}