package com.kbulkup.counseling.domain;

public class CounselingStatus {
    public static final String THREE_DAYS_LEFT = "3일 남음";
    public static final String TWO_DAYS_LEFT = "2일 남음";
    public static final String ONE_DAYS_LEFT = "1일 남음";
    public static final String EXPIRED = "만료";

    private CounselingStatus() {
        // Prevent instantiation
    }
}
