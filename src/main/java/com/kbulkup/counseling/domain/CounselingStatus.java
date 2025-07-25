package com.kbulkup.counseling.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CounselingStatus {

    THREE_DAYS_LEFT("3일 남음"),
    TWO_DAYS_LEFT("2일 남음"),
    ONE_DAYS_LEFT("1일 남음"),
    EXPIRED("만료");

    private final String name;
}
