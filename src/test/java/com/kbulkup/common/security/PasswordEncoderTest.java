package com.kbulkup.common.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PasswordEncoderTest {

    @Test
    void generateAdminPassword() {
        // Spring 컨텍스트 로딩 없이, SecurityConfig에서 사용하는 것과 동일한 방식으로 PasswordEncoder를 직접 생성합니다.
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        assertNotNull(passwordEncoder, "PasswordEncoder가 생성되지 않았습니다.");

        String password = "admin";
        String encodedPassword = passwordEncoder.encode(password);

        System.out.println("==================================================");
        System.out.println("Encoded Password for 'admin': " + encodedPassword);
        System.out.println("==================================================");
    }
}