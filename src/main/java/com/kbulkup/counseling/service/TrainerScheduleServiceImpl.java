package com.kbulkup.counseling.service;

import com.kbulkup.common.exception.CounselingException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.counseling.domain.TrainerSchedule;
import com.kbulkup.counseling.dto.request.TrainerScheduleCreateRequestDTO;
import com.kbulkup.counseling.dto.response.TrainerScheduleResponseDTO;
import com.kbulkup.counseling.mapper.CounselingReservationMapper;
import com.kbulkup.counseling.mapper.TrainerScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerScheduleServiceImpl implements TrainerScheduleService {

    private final TrainerScheduleMapper trainerScheduleMapper;
    private final CounselingReservationMapper reservationMapper;

    private static final LocalTime BUSINESS_START = LocalTime.of(9, 0);
    private static final LocalTime BUSINESS_END = LocalTime.of(18, 0);
    private static final int SESSION_DURATION_MINUTES = 30;

    @Override
    @Transactional
    public void createSchedules(TrainerScheduleCreateRequestDTO dto, Long userId) {
        for (TrainerScheduleCreateRequestDTO.TimeSlotDTO timeSlotDTO : dto.getTimeSlots()) {
            validateTimeSlot(timeSlotDTO.getStartTime(), timeSlotDTO.getEndTime());
            validateNoDuplicateSchedule(userId, timeSlotDTO.getStartTime(), timeSlotDTO.getEndTime());

            TrainerSchedule schedule = TrainerSchedule.createSchedule(userId, timeSlotDTO.getStartTime(), timeSlotDTO.getEndTime(), true);
            trainerScheduleMapper.insertSchedule(schedule);
        }
    }

    @Override
    public List<TrainerScheduleResponseDTO> getSchedulesByTrainerId(Long trainerId) {
        return trainerScheduleMapper.findByTrainerId(trainerId)
                .stream()
                .map(TrainerScheduleResponseDTO::createDTO)
                .toList();
    }

    @Override
    @Transactional
    public void deleteSchedule(Long scheduleId, Long trainerId) {
        TrainerSchedule schedule = trainerScheduleMapper.findByScheduleId(scheduleId);

        if (schedule == null) {
            throw new CounselingException(ResponseCode.SCHEDULE_NOT_FOUND);
        }

        if (!schedule.getTrainerId().equals(trainerId)) {
            throw new CounselingException(ResponseCode.UNAUTHORIZED_SCHEDULE_ACCESS);
        }

        // 예약이 있는 스케줄은 삭제 불가
        if (reservationMapper.existsByScheduleId(scheduleId)) {
            throw new CounselingException(ResponseCode.SCHEDULE_HAS_RESERVATIONS);
        }

        trainerScheduleMapper.deleteSchedule(scheduleId);
    }

    private void validateTimeSlot(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isBefore(LocalDateTime.now())) {
            throw new CounselingException(ResponseCode.INVALID_PAST_TIME);
        }

        // 영업시간 체크 (9시~18시)
        LocalTime startLocalTime = startTime.toLocalTime();
        LocalTime endLocalTime = endTime.toLocalTime();

        if (startLocalTime.isBefore(BUSINESS_START) || endLocalTime.isAfter(BUSINESS_END)) {
            throw new CounselingException(ResponseCode.INVALID_BUSINESS_HOURS);
        }

        // 30분 단위 체크
        long minutes = ChronoUnit.MINUTES.between(startTime, endTime);
        if (minutes != SESSION_DURATION_MINUTES) {
            throw new CounselingException(ResponseCode.INVALID_SESSION_DURATION);
        }
    }

    private void validateNoDuplicateSchedule(Long trainerId, LocalDateTime startTime, LocalDateTime endTime) {
        if (trainerScheduleMapper.existsByTrainerIdAndTimeRange(trainerId, startTime, endTime)) {
            throw new CounselingException(ResponseCode.DUPLICATE_SCHEDULE);
        }
    }
}
