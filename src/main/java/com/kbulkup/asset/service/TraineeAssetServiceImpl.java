package com.kbulkup.asset.service;

import com.kbulkup.asset.domain.Composition;
import com.kbulkup.asset.domain.Snapshot;
import com.kbulkup.asset.domain.Transaction;
import com.kbulkup.asset.dto.response.TraineeAssetDetailResponseDTO;
import com.kbulkup.asset.mapper.TraineeAssetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TraineeAssetServiceImpl implements TraineeAssetService {

    private final TraineeAssetMapper traineeAssetMapper;

    @Override
    public TraineeAssetDetailResponseDTO getTraineeAsset(Long id) {
        List<Transaction> transactions = traineeAssetMapper.getTransactionsByTraineeId(id);
        List<Snapshot> snapshots = traineeAssetMapper.getSnapshotsByTraineeId(id);
        Composition compositionsByTraineeId = traineeAssetMapper.getCompositionsByTraineeId(id);

        return TraineeAssetDetailResponseDTO.toDTO(transactions, snapshots, compositionsByTraineeId);
    }
}
