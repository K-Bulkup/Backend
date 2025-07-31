package com.kbulkup.user.event.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRegisteredEventDataDTO {
    private Long userId;
    private String email;
    private String role;
    // 필요한 다른 필드 추가
}
