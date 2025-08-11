package com.kbulkup.statistics.service;

import com.kbulkup.statistics.dto.request.StatisticsTrainerReviewRequestDTO;
import com.kbulkup.statistics.dto.response.StatisticsTrainerReviewResponseDTO;

public interface StatisticsTrainerReviewService {
    StatisticsTrainerReviewResponseDTO getReviewStatistics(StatisticsTrainerReviewRequestDTO requestDTO);
}
