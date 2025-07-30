package com.kbulkup.auth.service;

import com.kbulkup.auth.dto.request.LoginRequestDTO;
import com.kbulkup.auth.dto.response.LoginResponseDTO;

public interface LoginStrategy {

    /**
     * 로그인 요청을 처리하고 결과를 반환합니다.
     * @param requestDTO 로그인에 필요한 정보를 담은 요청 DTO
     * @return 로그인 성공 시 사용자 정보 및 토큰을 담은 응답 DTO
     */
    LoginResponseDTO login(Object requestDTO);

    /**
     * 현재 전략이 어떤 로그인 방식을 처리하는지 식별합니다.
     * @return 이 전략이 담당하는 로그인 타입 (LOCAL, NAVER, KAKAO 등)
     */
    String getLoginType();
}