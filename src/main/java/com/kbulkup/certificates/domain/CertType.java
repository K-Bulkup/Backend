package com.kbulkup.certificates.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CertType {

    ASSET_MANAGER("투자자산운용사"),
    INVESTMENT_ANALYST("금융투자분석사"),
    RISK_MANAGER("재무위험관리사"),
    INVESTMENT_ADVISOR("투자권유자문인력"),
    INVESTMENT_AGENT("투자권유대행인");

    private final String name;

}
