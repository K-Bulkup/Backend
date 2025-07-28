package com.kbulkup.auth.service;

import com.kbulkup.auth.dto.request.LoginRequestDTO;
import com.kbulkup.auth.dto.request.SignupRequestDTO;
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.auth.dto.response.SignupResponseDTO;


public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO dto);

    SignupResponseDTO signup(SignupRequestDTO dto);

    void logout();
}