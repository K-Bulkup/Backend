package com.kbulkup.auth.service;

import com.kbulkup.auth.dto.request.SocialSignUpRequestDTO;
import com.kbulkup.auth.dto.request.SignupRequestDTO;
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.auth.dto.response.SignupResponseDTO;

public interface AuthService {

    /**
     * 일반 회원가입을 처리합니다.
     * @param dto 회원가입 요청 DTO
     * @return 회원가입 응답 DTO
     */
    SignupResponseDTO signup(SignupRequestDTO dto);

    /**
     * 소셜 로그인 후 사용자의 역할을 설정하고 최종 회원가입을 완료합니다.
     * @param dto 소셜 회원가입 요청 DTO (임시 토큰, 역할 정보 포함)
     * @return 최종 로그인 응답 DTO
     */
    LoginResponseDTO socialSignUp(SocialSignUpRequestDTO dto);

    /**
     * 사용자 로그아웃을 처리합니다.
     */
    void logout();
}