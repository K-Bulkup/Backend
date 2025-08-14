package com.kbulkup.counseling.service;

import com.kbulkup.counseling.dto.request.TrainerScheduleCreateRequestDTO;
import com.kbulkup.counseling.dto.response.TrainerScheduleResponseDTO;

import java.util.List;

public interface TrainerScheduleService {

    void createSchedules(TrainerScheduleCreateRequestDTO requestDTO, Long userId);

    List<TrainerScheduleResponseDTO> getSchedulesByTrainerId(Long trainerId);

    void deleteSchedule(Long scheduleId, Long trainerId);
}
