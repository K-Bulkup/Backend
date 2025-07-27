package com.kbulkup.auth.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class LoginRequestDTO {
    private String loginType;
    private String email;
    private String password;
    private String code;
    private String role;
}