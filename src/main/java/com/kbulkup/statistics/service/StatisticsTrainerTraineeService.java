package com.kbulkup.statistics.service;

import com.kbulkup.statistics.dto.request.StatisticsTrainerTraineeRequestDTO;
import com.kbulkup.statistics.dto.response.StatisticsTrainerTraineeResponseDTO;

public interface StatisticsTrainerTraineeService {
    StatisticsTrainerTraineeResponseDTO getTraineeStatistics(StatisticsTrainerTraineeRequestDTO requestDTO);
}
