package com.kbulkup.auth.service;

import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.auth.dto.response.SignupResponseDTO;
import com.kbulkup.auth.domain.LoginType;
import com.kbulkup.user.domain.RoleType;

import java.time.LocalDate;

public interface AuthService {

    LoginResponseDTO login(LoginType loginType, String email, String password, String code, RoleType role);

    SignupResponseDTO signup(String userId, String password, String name, String email, String phone, String address, RoleType role, LoginType loginType, String providerId, String birthdate);

    void logout();
}