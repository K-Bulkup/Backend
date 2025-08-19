package com.kbulkup.asset.dto.response;

import com.kbulkup.asset.domain.Composition;
import com.kbulkup.asset.domain.Snapshot;
import com.kbulkup.asset.domain.Transaction;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@ApiModel(description = "수강생 자산 상세 응답")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineeAssetDetailResponseDTO {

    @ApiModelProperty(value = "거래내역 목록")
    private List<Transaction> transactions;

    @ApiModelProperty(value = "스냅샷 목록")
    private List<Snapshot> snapshots;

    @ApiModelProperty(value = "자산 구성")
    private Composition composition;

    public static TraineeAssetDetailResponseDTO toDTO(List<Transaction> transactions, List<Snapshot> snapshots, Composition composition) {
        return TraineeAssetDetailResponseDTO.builder()
                .transactions(transactions)
                .snapshots(snapshots)
                .composition(composition)
                .build();
    }
}
