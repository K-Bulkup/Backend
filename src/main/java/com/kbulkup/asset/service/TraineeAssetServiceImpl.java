package com.kbulkup.asset.service;

import com.kbulkup.asset.domain.Composition;
import com.kbulkup.asset.domain.Snapshot;
import com.kbulkup.asset.domain.Transaction;
import com.kbulkup.asset.dto.request.TokenRequestDTO;
import com.kbulkup.asset.dto.response.ExternalAssetResponseDTO;
import com.kbulkup.asset.dto.response.ExternalTokenResponseDTO;
import com.kbulkup.asset.dto.response.TraineeAssetDetailResponseDTO;
import com.kbulkup.asset.mapper.TraineeAssetMapper;
import com.kbulkup.common.security.JwtTokenProvider;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TraineeAssetServiceImpl implements TraineeAssetService {

    private final TraineeAssetMapper traineeAssetMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public TraineeAssetDetailResponseDTO getTraineeAsset(Long id) {
        List<Transaction> transactions = traineeAssetMapper.getTransactionsByTraineeId(id);
        List<Snapshot> snapshots = traineeAssetMapper.getSnapshotsByTraineeId(id);
        Composition compositionsByTraineeId = traineeAssetMapper.getCompositionsByTraineeId(id);

        return TraineeAssetDetailResponseDTO.toDTO(transactions, snapshots, compositionsByTraineeId);
    }

    @Override
    @Transactional
    public void createUserPortfolio(String bank, User user) {
        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getUserId(), user.getRoles());
        ExternalTokenResponseDTO externalTokenResponseDTO = getAccessToken(bank, accessToken);
        ExternalAssetResponseDTO externalAssetResponseDTO = getUserAssetData(externalTokenResponseDTO.getAccessToken());
        insertTraineeAsset(user.getUserId(), externalAssetResponseDTO.getTraineeAssetDetailResponseDTO());
    }

    @Transactional
    public void insertTraineeAsset(Long id, TraineeAssetDetailResponseDTO dto) {
        traineeAssetMapper.insertPortfolio(id);
        traineeAssetMapper.insertTransactions(id, dto.getTransactions());
        traineeAssetMapper.insertSnapshots(id, dto.getSnapshots());
        traineeAssetMapper.insertComposition(id, dto.getComposition());
    }

    private ExternalTokenResponseDTO getAccessToken(String bank, String accessToken) {
        WebClient webClient = WebClient
                .builder()
                .baseUrl("http://localhost:9080")
                .build();
        return webClient.post()
                .uri("/external-api/token")
                .header("Authorization", "Bearer " + accessToken)
                .bodyValue(TokenRequestDTO.create(bank))
                .retrieve()
                .bodyToMono(ExternalTokenResponseDTO.class)
                .block();
    }

    private ExternalAssetResponseDTO getUserAssetData(String token) {
        WebClient webClient = WebClient
                .builder()
                .baseUrl("http://localhost:9080")
                .build();

        return webClient.post()
                .uri("/external-api/user-data")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(ExternalAssetResponseDTO.class)
                .block();
    }

    @Override
    public TraineeAssetDetailResponseDTO findTraineeAssetDetailByRoomID(String roomId) {
        Long userId = traineeAssetMapper.findUserIdByRoomID(roomId);
        return getTraineeAsset(userId);
    }
}
