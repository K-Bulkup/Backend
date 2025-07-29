package com.kbulkup.training.service;

import com.kbulkup.training.dto.response.TrainerDetailResponseDTO;

public interface TrainerService {
    TrainerDetailResponseDTO getTrainerDetail(Long trainerId);
}
