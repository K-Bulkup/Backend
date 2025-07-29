package com.kbulkup.asset.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Long transactionId;
    private Long userId;
    private String transactionType;
    private Long amount;
    private TransactionCategory transactionCategory;
    private LocalDate tranDate;
}
