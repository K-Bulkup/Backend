package com.kbulkup.auth.dto.request;

import com.kbulkup.auth.domain.LoginType;
import com.kbulkup.user.domain.RoleType;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class LoginRequestDTO {
    private LoginType loginType;
    private String email;
    private String password;
    private String code;
    private RoleType role;
}