package com.kbulkup.counseling.mapper;

import com.kbulkup.counseling.domain.CounselingReservation;
import com.kbulkup.counseling.dto.response.CounselingListResponseDTO;
import com.kbulkup.counseling.dto.response.CounselingReservationResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CounselingReservationMapper {

    void insertReservation(CounselingReservation reservation);

    CounselingReservation findByReservationId(@Param("reservationId") Long reservationId);

    List<CounselingReservationResponseDTO> findByTraineeId(@Param("traineeId") Long traineeId);

    List<CounselingReservationResponseDTO> findByTrainerId(@Param("trainerId") Long trainerId);

    CounselingReservation findByRoomId(@Param("roomId") String roomId);

    boolean existsByScheduleId(@Param("scheduleId") Long scheduleId);

    void updateStatus(@Param("reservationId") Long reservationId, @Param("status") String status);

    void updateStatusToActive(@Param("reservationId") Long reservationId);

    void updateStatusToCompleted(@Param("reservationId") Long reservationId);

    void updateStatusToCanceled(@Param("reservationId") Long reservationId);

    List<CounselingReservation> findReservationsToActivate(@Param("currentTime") LocalDateTime currentTime);

    List<CounselingReservation> findReservationsToComplete(@Param("currentTime") LocalDateTime currentTime);


    List<CounselingListResponseDTO> findChatRoomsByUserId(@Param("userId") Long userId);
    CounselingReservation findByTraineeAndTrainer(@Param("traineeId") Long traineeId, @Param("trainerId") Long trainerId);
    Boolean existsByUserIdAndTrainingId(@Param("userId") Long userId, @Param("trainingId") Long trainingId);
    void updateLatestMessage(@Param("roomId") String roomId, @Param("latestMessage") String latestMessage, @Param("latestAt") LocalDateTime latestAt);
    void updateStatusToExpired(@Param("roomId") String roomId, @Param("status") String status);
}
