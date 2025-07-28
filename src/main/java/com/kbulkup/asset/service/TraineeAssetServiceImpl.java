package com.kbulkup.asset.service;

import com.kbulkup.asset.domain.Composition;
import com.kbulkup.asset.domain.Snapshot;
import com.kbulkup.asset.domain.Transaction;
import com.kbulkup.asset.dto.request.TokenRequestDTO;
import com.kbulkup.asset.dto.response.TokenResponseDTO;
import com.kbulkup.asset.dto.response.TraineeAssetDetailResponseDTO;
import com.kbulkup.asset.mapper.TraineeAssetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

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

    @Override
    @Transactional
    public void createUserPortfolio(String bank, Long id) {
        TokenResponseDTO dto = getAccessToken(bank, id);
        insertTraineeAsset(id, getUserAssetData(dto.getAccessToken()));
    }

    @Transactional
    public void insertTraineeAsset(Long id, TraineeAssetDetailResponseDTO dto) {
        traineeAssetMapper.insertTransactions(id, dto.getTransactions());
        traineeAssetMapper.insertSnapshots(id, dto.getSnapshots());
        traineeAssetMapper.insertComposition(id, dto.getComposition());
    }

    private TokenResponseDTO getAccessToken(String bank, Long id) {
        WebClient webClient = WebClient
                .builder()
                .baseUrl("http://localhost:8888")
                .build();
        return webClient.post()
                .uri("/external-api/token")
                .header("X-User-Id", String.valueOf(id))
                .bodyValue(TokenRequestDTO.create(bank))
                .retrieve()
                .bodyToMono(TokenResponseDTO.class)
                .block();
    }

    private TraineeAssetDetailResponseDTO getUserAssetData(String token) {
        WebClient webClient = WebClient
                .builder()
                .baseUrl("http://localhost:8888")
                .build();

        return webClient.post()
                .uri("/external-api/user-data")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(TraineeAssetDetailResponseDTO.class)
                .block();
    }
}
