package com.kbulkup.auth.naver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverProfile {

    /**
     * 네이버에서 제공하는 사용자의 고유 식별자입니다.
     * 이 값은 로그인 타입(네이버)과 함께 사용자를 식별하는 키가 됩니다.
     */
    @JsonProperty("id")
    private String providerId;

    /**
     * 사용자의 이메일 주소입니다.
     */
    private String email;

    /**
     * 사용자의 이름(닉네임)입니다.
     */
    private String name;

}