package com.kbulkup.counseling.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CounselingStatus {

    PROGRESS("진행중"),
    EXPIRED("만료");

    private final String name;
}
