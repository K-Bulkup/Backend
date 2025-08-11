package com.kbulkup.statistics.service;

import com.kbulkup.statistics.dto.request.StatisticsTrainerTraineeRequestDTO;
import com.kbulkup.statistics.dto.response.StatisticsTrainerTraineeResponseDTO;
import com.kbulkup.statistics.mapper.StatisticsTrainerTraineeMapper;
import org.springframework.stereotype.Service;

@Service
public class StatisticsTrainerTraineeServiceImpl implements StatisticsTrainerTraineeService {

    private final StatisticsTrainerTraineeMapper statisticsTrainerTraineeMapper;

    public StatisticsTrainerTraineeServiceImpl(StatisticsTrainerTraineeMapper statisticsTrainerTraineeMapper) {
        this.statisticsTrainerTraineeMapper = statisticsTrainerTraineeMapper;
    }

    @Override
    public StatisticsTrainerTraineeResponseDTO getTraineeStatistics(StatisticsTrainerTraineeRequestDTO requestDTO) {
        return null; // 메서드 내부를 비워둡니다.
    }
}
