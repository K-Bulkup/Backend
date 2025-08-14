package com.kbulkup.counseling.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {

    RESERVED("예약완료"),
    ACTIVE("진행중"),
    COMPLETED("완료"),
    CANCELED("취소");

    private final String name;
}
