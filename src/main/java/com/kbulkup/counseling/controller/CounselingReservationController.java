package com.kbulkup.counseling.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.counseling.dto.request.ReservationCreateRequestDTO;
import com.kbulkup.counseling.dto.response.CounselingReservationResponseDTO;
import com.kbulkup.counseling.service.CounselingReservationService;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class CounselingReservationController {

    private final CounselingReservationService counselingReservationService;

    @PostMapping("/trainee")
    public CustomResponse<Void> createReservations(@AuthenticationPrincipal(expression = "user") User user, @RequestBody ReservationCreateRequestDTO dto) {
        counselingReservationService.createReservations(dto, user.getUserId());
        return CustomResponse.success(ResponseCode.SUCCESS);
    }

    @GetMapping("/trainee")
    public CustomResponse<List<CounselingReservationResponseDTO>> getReservationsByTraineeId(@AuthenticationPrincipal(expression = "user") User user) {
        return CustomResponse.success(ResponseCode.SUCCESS, counselingReservationService.getReservationsByTraineeId(user.getUserId()));
    }
    
    @GetMapping("/trainer")
    public CustomResponse<List<CounselingReservationResponseDTO>> getReservationsByTrainerId(@AuthenticationPrincipal(expression = "user") User user) {
        return CustomResponse.success(ResponseCode.SUCCESS, counselingReservationService.getReservationsByTrainerId(user.getUserId()));
    }

    @DeleteMapping("/{reservationId}")
    public CustomResponse<Void> cancelReservations(@PathVariable Long reservationId, @AuthenticationPrincipal(expression = "user") User user) {
        counselingReservationService.cancelReservation(reservationId, user.getUserId());
        return CustomResponse.success(ResponseCode.SUCCESS);
    }
}
