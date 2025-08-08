package com.kbulkup.statistics.mapper;

import com.kbulkup.statistics.dto.response.StatisticsTrainerRevenueResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StatisticsTrainerRevenueMapper {
    StatisticsTrainerRevenueResponseDTO getTotalAndLast30DaysRevenue(@Param("trainerId") Long trainerId);
    List<StatisticsTrainerRevenueResponseDTO.TrainingRevenueDTO> getTrainingRevenueDetails(@Param("trainerId") Long trainerId);
}