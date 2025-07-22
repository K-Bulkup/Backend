package com.kbulkup.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialUserInfoDto {
    private String email;
    private String providerId;
    private String loginType;
    private String username;
}
