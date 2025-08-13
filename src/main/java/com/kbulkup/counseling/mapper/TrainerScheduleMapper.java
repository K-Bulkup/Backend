package com.kbulkup.counseling.mapper;

import com.kbulkup.counseling.domain.TrainerSchedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TrainerScheduleMapper {

    void insertSchedule(TrainerSchedule schedule);

    List<TrainerSchedule> findByTrainerId(@Param("trainerId") Long trainerId);

    List<TrainerSchedule> findByTrainerIdAndDateRange(
            @Param("trainerId") Long trainerId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    TrainerSchedule findByScheduleId(@Param("scheduleId") Long scheduleId);

    boolean existsByTrainerIdAndTimeRange(
            @Param("trainerId") Long trainerId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    void updateAvailability(@Param("scheduleId") Long scheduleId, @Param("isAvailable") Boolean isAvailable);

    void deleteSchedule(@Param("scheduleId") Long scheduleId);

    Long findScheduleIdByTime(@Param("trainerId") Long trainerId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
