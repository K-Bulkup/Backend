package com.kbulkup.auth.service;

import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.auth.dto.response.SignupResponseDTO;

import java.time.LocalDate;

public interface AuthService {

    LoginResponseDTO login(String loginType, String email, String password, String code, String role);

    SignupResponseDTO signup(String userId, String password, String name, String email, String phone, String address, String role, String loginType, String providerId, LocalDate birthdate);

    void logout();
}