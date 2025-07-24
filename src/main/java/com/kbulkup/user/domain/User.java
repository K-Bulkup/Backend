package com.kbulkup.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private long userId;
    private String email;
    private String password;
    private String username;
    private String loginType;
    private String providerId; // 소셜 로그인 제공자 ID
    private LocalDate birthdate;
    
    private String userProfileUrl;
    private boolean isDeleted;
    private int growthScore;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<String> roles;

    public static User createUser(User user) {
        return '='
    }
}
