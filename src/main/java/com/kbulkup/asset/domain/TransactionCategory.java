package com.kbulkup.asset.domain;

import com.kbulkup.common.exception.EnumException;
import com.kbulkup.common.response.ResponseCode;

public enum TransactionCategory {
    주거_공과금("주거/공과금"),
    문화생활_여가("문화생활/여가"),
    패션_미용("패션/미용"),
    기타("기타"),
    월급("월급"),
    부수입("부수입"),
    식비("식비"),
    교통비("교통비"),
    생필품("생필품"),
    의료_건강("의료/건강");

    private final String dbValue;

    TransactionCategory(String dbValue) {
        this.dbValue = dbValue;
    }

    public String toDbValue() {
        return dbValue;
    }

    public static TransactionCategory fromDbValue(String dbValue) {
        for (TransactionCategory tc : values()) {
            if (tc.dbValue.equals(dbValue)) {
                return tc;
            }
        }
        throw new EnumException(ResponseCode.INVALID_ENUM);
    }
}
