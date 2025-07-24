package com.kbulkup.auth.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 회원가입 성공 시 클라이언트에 반환될 응답 데이터를 담는 DTO
 */
@Data
@Builder
public class SignupResponseDTO {

    private String message; // 응답 메시지 (예: "회원가입 성공")
    private Long userId;    // 생성된 사용자의 고유 ID
    private String email;   // 생성된 사용자의 이메일
    private String username; // 생성된 사용자의 이름(닉네임)

}