package com.kbulkup.counseling.service;

import com.kbulkup.common.exception.CounselingException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.counseling.domain.CounselingReservation;
import com.kbulkup.counseling.domain.ReservationStatus;
import com.kbulkup.counseling.domain.TrainerSchedule;
import com.kbulkup.counseling.dto.request.ReservationCreateRequestDTO;
import com.kbulkup.counseling.dto.response.CounselingReservationResponseDTO;
import com.kbulkup.counseling.mapper.CounselingReservationMapper;
import com.kbulkup.counseling.mapper.TrainerScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CounselingReservationServiceImpl implements CounselingReservationService {

    private final CounselingReservationMapper reservationMapper;
    private final TrainerScheduleMapper scheduleMapper;

    @Override
    @Transactional
    public void createReservations(ReservationCreateRequestDTO dto, Long userId) {

        Long scheduleId = scheduleMapper.findScheduleIdByTime(dto.getTrainerId(), dto.getStartTime(), dto.getEndTime());
        TrainerSchedule schedule = scheduleMapper.findByScheduleId(scheduleId);

        if (schedule == null) {
            throw new CounselingException(ResponseCode.SCHEDULE_NOT_FOUND);
        }
        if (!schedule.getIsAvailable()) {
            throw new CounselingException(ResponseCode.SCHEDULE_NOT_AVAILABLE);
        }
        // 이미 예약된 스케줄인지
        if (reservationMapper.existsByScheduleId(scheduleId)) {
            throw new CounselingException(ResponseCode.SCHEDULE_ALREADY_RESERVED);
        }
        // 트레이너 ID 검증
        if (!schedule.getTrainerId().equals(dto.getTrainerId())) {
            throw new CounselingException(ResponseCode.INVALID_TRAINER_SCHEDULE);
        }

        String roomId = "room-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12);

        CounselingReservation reservation = CounselingReservation.createReservation(userId, dto.getTrainerId(), dto.getTrainingId(), scheduleId, roomId);
        reservationMapper.insertReservation(reservation);

        scheduleMapper.updateAvailability(scheduleId, false);
    }

    @Override
    public List<CounselingReservationResponseDTO> getReservationsByTraineeId(Long userId) {
        return reservationMapper.findByTraineeId(userId);
    }

    @Override
    public List<CounselingReservationResponseDTO> getReservationsByTrainerId(Long userId) {
        return reservationMapper.findByTrainerId(userId);
    }

    @Override
    @Transactional
    public void cancelReservation(Long reservationId, Long userId) {
        CounselingReservation reservation = reservationMapper.findByReservationId(reservationId);

        if (reservation == null) {
            throw new CounselingException(ResponseCode.RESERVATION_NOT_FOUND);
        }

        // 권한 확인 (트레이니만 취소 가능)
        if (!reservation.getTraineeId().equals(userId)) {
            throw new CounselingException(ResponseCode.UNAUTHORIZED_SCHEDULE_ACCESS);
        }

        // 진행중이거나 완료된 예약은 취소 불가
        String currentStatus = reservation.getStatus();
        if (ReservationStatus.ACTIVE.getName().equals(currentStatus) || ReservationStatus.COMPLETED.getName().equals(currentStatus)) {
            throw new CounselingException(ResponseCode.CANNOT_CANCEL_RESERVATION);
        }

        // 예약 취소 & 트레이너 스케줄 가용성 복구
        reservationMapper.updateStatusToCanceled(reservationId);
        scheduleMapper.updateAvailability(reservation.getScheduleId(), true);
    }

    @Override
    @Transactional
    public void activateReservations() {
        List<CounselingReservation> reservations = reservationMapper.findReservationsToActivate(LocalDateTime.now());
        for (CounselingReservation reservation : reservations) {
            reservationMapper.updateStatusToActive(reservation.getReservationId());
        }
    }

    @Override
    @Transactional
    public void completeReservations() {
        List<CounselingReservation> reservations = reservationMapper.findReservationsToComplete(LocalDateTime.now());
        for (CounselingReservation reservation : reservations) {
            reservationMapper.updateStatusToCompleted(reservation.getReservationId());
        }
    }
}
