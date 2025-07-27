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
public class Snapshot {
    private Long snapshotId;
    private Long userId;
    private Long balance;
    private LocalDate snapshotDate;
}
