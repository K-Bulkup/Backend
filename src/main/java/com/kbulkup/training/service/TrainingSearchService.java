package com.kbulkup.training.service;

import com.kbulkup.training.dto.request.TrainingSearchListRequestDTO;
import com.kbulkup.training.dto.response.TrainingSearchListResponseDTO;

import java.util.List;

public interface TrainingSearchService {
    List<TrainingSearchListResponseDTO> searchTrainings(TrainingSearchListRequestDTO request);
}
