package com.kbulkup.counseling.service;

import com.kbulkup.counseling.dto.request.ReservationCreateRequestDTO;
import com.kbulkup.counseling.dto.response.CounselingReservationResponseDTO;

import java.util.List;

public interface CounselingReservationService {

    void createReservations(ReservationCreateRequestDTO dto, Long userId);

    List<CounselingReservationResponseDTO> getReservationsByTraineeId(Long userId);

    List<CounselingReservationResponseDTO> getReservationsByTrainerId(Long userId);

    void cancelReservation(Long reservationId, Long userId);

    void activateReservations();

    void completeReservations();
}
