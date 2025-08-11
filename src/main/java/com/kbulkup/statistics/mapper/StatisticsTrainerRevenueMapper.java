package com.kbulkup.statistics.mapper;

import com.kbulkup.statistics.dto.response.DailyRevenueDTO;
import com.kbulkup.statistics.dto.response.MonthlyRevenueDTO;
import com.kbulkup.statistics.dto.response.StatisticsTrainerRevenueResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StatisticsTrainerRevenueMapper {
    StatisticsTrainerRevenueResponseDTO getTotalAndLast30DaysRevenue(@Param("trainerId") Long trainerId);
    List<StatisticsTrainerRevenueResponseDTO.TrainingRevenueDTO> getTrainingRevenueDetails(@Param("trainerId") Long trainerId);

    List<DailyRevenueDTO> getDailyRevenueLast30Days(@Param("trainerId") Long trainerId);

    List<MonthlyRevenueDTO> getMonthlyRevenue(@Param("trainerId") Long trainerId);

    List<DailyRevenueDTO> getDailyRevenueForTraining(@Param("trainerId") Long trainerId, @Param("trainingId") Long trainingId);

    List<MonthlyRevenueDTO> getMonthlyRevenueForTraining(@Param("trainerId") Long trainerId, @Param("trainingId") Long trainingId);
}