package com.kbulkup.asset.service;

import com.kbulkup.asset.domain.Composition;
import com.kbulkup.asset.domain.Snapshot;
import com.kbulkup.asset.domain.Transaction;
import com.kbulkup.asset.dto.request.FintechAuthRequestDTO;
import com.kbulkup.asset.dto.request.TokenRequestDTO;
import com.kbulkup.asset.dto.response.ExternalAccessTokenResponseDTO;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        ExternalTokenResponseDTO externalTokenResponseDTO = getAccessTokenAndFintechUseNum(bank, accessToken);
        traineeAssetMapper.insertFintechUseNum(user.getUserId(), bank, externalTokenResponseDTO.getFintechUseNum());
        ExternalAssetResponseDTO externalAssetResponseDTO = getUserAssetData(externalTokenResponseDTO.getAccessToken(), externalTokenResponseDTO.getFintechUseNum());
        traineeAssetMapper.insertPortfolio(user.getUserId());
        insertTraineeAsset(user.getUserId(), externalAssetResponseDTO.getTraineeAssetDetailResponseDTO());
        traineeAssetMapper.insertComposition(user.getUserId(), externalAssetResponseDTO.getTraineeAssetDetailResponseDTO().getComposition());
    }

    @Override
    @Transactional
    public void updateUserPortfolio(User user) {
        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getUserId(), user.getRoles());
        FintechAuthRequestDTO fintechAuthRequestDTO = traineeAssetMapper.findBankAndFintechUseNum(user.getUserId());
        ExternalAccessTokenResponseDTO externalAccessTokenResponseDTO = getAccessToken(accessToken, fintechAuthRequestDTO.getFintechUseNum());
        ExternalAssetResponseDTO externalAssetResponseDTO = getUserAssetData(externalAccessTokenResponseDTO.getAccessToken(), fintechAuthRequestDTO.getFintechUseNum());
        deleteTraineeAsset(user.getUserId(), externalAssetResponseDTO.getTraineeAssetDetailResponseDTO());
        insertTraineeAsset(user.getUserId(), externalAssetResponseDTO.getTraineeAssetDetailResponseDTO());
    }

    @Transactional
    public void deleteTraineeAsset(Long id, TraineeAssetDetailResponseDTO dto) {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusMonths(3).withDayOfMonth(1);

        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);

        traineeAssetMapper.deleteTransactionsWindow(id, startDateTime, endDateTime);
        traineeAssetMapper.deleteSnapshotsWindow(id, startDateTime, endDateTime);
    }

    @Transactional
    public void insertTraineeAsset(Long id, TraineeAssetDetailResponseDTO dto) {
        traineeAssetMapper.insertTransactions(id, dto.getTransactions());
        traineeAssetMapper.insertSnapshots(id, dto.getSnapshots());
    }

    private ExternalTokenResponseDTO getAccessTokenAndFintechUseNum(String bank, String accessToken) {
        WebClient webClient = WebClient
                .builder()
                .baseUrl("http://13.125.89.72:9080")
                .build();
        return webClient.post()
                .uri("/external-api/create-user")
                .header("Authorization", "Bearer " + accessToken)
                .bodyValue(TokenRequestDTO.create(bank))
                .retrieve()
                .bodyToMono(ExternalTokenResponseDTO.class)
                .block();
    }

    private ExternalAccessTokenResponseDTO getAccessToken(String accessToken, String fintechUseNum) {
        WebClient webClient = WebClient
                .builder()
                .baseUrl("http://13.125.89.72:9080")
                .build();
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/external-api/token")
                        .queryParam("fintechUseNum", fintechUseNum) // 쿼리 파라미터 추가
                        .build())
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(ExternalAccessTokenResponseDTO.class)
                .block();
    }

    private ExternalAssetResponseDTO getUserAssetData(String token, String fintechUseNum) {
        WebClient webClient = WebClient
                .builder()
                .baseUrl("http://13.125.89.72:9080")
                .build();

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/external-api/user-data")
                        .queryParam("fintechUseNum", fintechUseNum)
                        .build()
                )
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
