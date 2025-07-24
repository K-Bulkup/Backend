package com.kbulkup.auth.dto.response;

import com.kbulkup.user.domain.User;
import lombok.Builder;
import lombok.Data;

/**
 * 회원가입 성공 시 클라이언트에 반환될 응답 데이터를 담는 DTO
 */
@Data
@Builder
public class SignupResponseDTO {

    private Long userId;    // 생성된 사용자의 고유 ID
    private String email;   // 생성된 사용자의 이메일
    private String username; // 생성된 사용자의 이름(닉네임)

    public static SignupResponseDTO toDTO(User user) {
        return SignupResponseDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

}