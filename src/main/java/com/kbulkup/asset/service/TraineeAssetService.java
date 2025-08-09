package com.kbulkup.asset.service;

import com.kbulkup.asset.dto.response.TraineeAssetDetailResponseDTO;
import com.kbulkup.user.domain.User;

public interface TraineeAssetService {

    TraineeAssetDetailResponseDTO getTraineeAsset(Long id);

    void createUserPortfolio(String bank, User user);

    void updateUserPortfolio(User user);

    TraineeAssetDetailResponseDTO findTraineeAssetDetailByRoomID(String roomId);
}
