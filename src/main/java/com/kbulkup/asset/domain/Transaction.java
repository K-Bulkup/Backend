package com.kbulkup.asset.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Long transactionId;
    private Long userId;
    private String transactionType;
    private Long amount;
    private String transactionCategory;
    private LocalDateTime tranDate;
}
