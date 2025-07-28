package com.kbulkup.asset.service;

import com.kbulkup.asset.dto.response.TraineeAssetDetailResponseDTO;

public interface TraineeAssetService {

    TraineeAssetDetailResponseDTO getTraineeAsset(Long id);
}
