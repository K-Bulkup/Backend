package com.kbulkup.counseling.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.counseling.dto.request.ReservationCreateRequestDTO;
import com.kbulkup.counseling.dto.response.CounselingReservationResponseDTO;
import com.kbulkup.counseling.service.CounselingReservationService;
import com.kbulkup.user.domain.User;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "Counseling Reservation", description = "상담 예약 생성/조회/취소 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class CounselingReservationController {

    private final CounselingReservationService counselingReservationService;

    @ApiOperation(value = "상담 예약 생성(수강생)", notes = "수강생이 상담 예약을 생성합니다.")
    @PostMapping("/trainee")
    public CustomResponse<Void> createReservations(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user,
            @ApiParam(value = "예약 생성 요청", required = true)
            @RequestBody ReservationCreateRequestDTO dto) {
        counselingReservationService.createReservations(dto, user.getUserId());
        return CustomResponse.success(ResponseCode.SUCCESS);
    }

    @ApiOperation(value = "내 상담 예약 목록(수강생)", notes = "수강생 본인의 예약 목록을 조회합니다.")
    @GetMapping("/trainee")
    public CustomResponse<List<CounselingReservationResponseDTO>> getReservationsByTraineeId(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {
        return CustomResponse.success(ResponseCode.SUCCESS, counselingReservationService.getReservationsByTraineeId(user.getUserId()));
    }

    @ApiOperation(value = "내 상담 예약 목록(트레이너)", notes = "트레이너 본인의 예약 목록을 조회합니다.")
    @GetMapping("/trainer")
    public CustomResponse<List<CounselingReservationResponseDTO>> getReservationsByTrainerId(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {
        return CustomResponse.success(ResponseCode.SUCCESS, counselingReservationService.getReservationsByTrainerId(user.getUserId()));
    }

    @ApiOperation(value = "예약 취소", notes = "예약 ID로 상담 예약을 취소합니다.")
    @ApiImplicitParam(name = "reservationId", value = "예약 ID", required = true, dataType = "long", paramType = "path")
    @DeleteMapping("/{reservationId}")
    public CustomResponse<Void> cancelReservations(
            @PathVariable Long reservationId,
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {
        counselingReservationService.cancelReservation(reservationId, user.getUserId());
        return CustomResponse.success(ResponseCode.SUCCESS);
    }
}
