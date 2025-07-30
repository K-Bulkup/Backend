package com.kbulkup.user.domain;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class User {
    private Long userId;
    private String email;
    private String password;
    private String username;
    private String loginType;
    private String providerId;
    private LocalDateTime birthdate;
    
    private String userProfileUrl;
    private boolean isDeleted;
    private int growthScore;
    private List<String> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static User createUser(String email, String password, String username, String loginType, String providerId, LocalDateTime birthdate) {
        return User.builder().
                email(email).
                password(password).
                username(username).
                loginType(loginType).
                providerId(providerId).
                birthdate(birthdate).
                createdAt(LocalDateTime.now()).
                updatedAt(LocalDateTime.now()).
                build();
    }
}
