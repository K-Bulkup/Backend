package com.kbulkup.auth.naver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverProfileResponse {

    /**
     * API 호출 결과 코드입니다.
     * "00"이면 성공, 그 외의 값은 실패를 의미합니다.
     */
    private String resultcode;

    /**
     * API 호출 결과 메시지입니다.
     * "success" 또는 실패 메시지가 포함됩니다.
     */
    private String message;

    /**
     * 실제 사용자의 상세 프로필 정보를 담고 있는 객체입니다.
     * NaverProfile DTO와 매핑됩니다.
     */
    private NaverProfile response;
}