package com.kbulkup.asset.dto.response;

import com.kbulkup.asset.domain.Composition;
import com.kbulkup.asset.domain.Snapshot;
import com.kbulkup.asset.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineeAssetDetailResponseDTO {
    private List<Transaction> transactions;
    private List<Snapshot> snapshots;
    private Composition composition;

    public static TraineeAssetDetailResponseDTO toDTO(List<Transaction> transactions, List<Snapshot> snapshots, Composition composition) {
        return TraineeAssetDetailResponseDTO.builder()
                .transactions(transactions)
                .snapshots(snapshots)
                .composition(composition)
                .build();
    }
}
