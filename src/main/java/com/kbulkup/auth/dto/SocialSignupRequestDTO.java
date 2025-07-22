package com.kbulkup.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialSignupRequestDTO {

    @NotBlank(message = "임시 토큰은 필수입니다.")
    private String preAuthToken;

    @NotBlank(message = "역할은 필수 선택 사항입니다.")
    private String role; // "TRAINEE", "TRAINER"
}
