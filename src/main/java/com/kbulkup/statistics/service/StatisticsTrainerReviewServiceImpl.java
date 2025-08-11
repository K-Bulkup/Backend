package com.kbulkup.statistics.service;

import com.kbulkup.statistics.dto.request.StatisticsTrainerReviewRequestDTO;
import com.kbulkup.statistics.dto.response.StatisticsTrainerReviewResponseDTO;
import com.kbulkup.statistics.mapper.StatisticsTrainerReviewMapper;
import org.springframework.stereotype.Service;

@Service
public class StatisticsTrainerReviewServiceImpl implements StatisticsTrainerReviewService {

    private final StatisticsTrainerReviewMapper statisticsTrainerReviewMapper;

    public StatisticsTrainerReviewServiceImpl(StatisticsTrainerReviewMapper statisticsTrainerReviewMapper) {
        this.statisticsTrainerReviewMapper = statisticsTrainerReviewMapper;
    }

    @Override
    public StatisticsTrainerReviewResponseDTO getReviewStatistics(StatisticsTrainerReviewRequestDTO requestDTO) {
        return null; // 메서드 내부를 비워둡니다.
    }
}
