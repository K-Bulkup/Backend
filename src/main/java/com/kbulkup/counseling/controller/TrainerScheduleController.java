package com.kbulkup.counseling.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.counseling.dto.request.TrainerScheduleCreateRequestDTO;
import com.kbulkup.counseling.dto.response.TrainerScheduleResponseDTO;
import com.kbulkup.counseling.service.TrainerScheduleService;
import com.kbulkup.user.domain.User;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "Trainer Schedule", description = "트레이너 스케줄 등록/조회/삭제 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class TrainerScheduleController {

    private final TrainerScheduleService trainerScheduleService;

    @ApiOperation(
            value = "스케줄 등록(트레이너)",
            notes = "30분 단위/영업시간 내 등 비즈니스 규칙이 적용됩니다."
    )
    @PostMapping("/trainer")
    public CustomResponse<Void> createSchedules(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user,
            @ApiParam(value = "스케줄 등록 요청(타임슬롯 배열)", required = true)
            @RequestBody TrainerScheduleCreateRequestDTO dto) {
        trainerScheduleService.createSchedules(dto, user.getUserId());
        return CustomResponse.success(ResponseCode.SUCCESS);
    }

    @ApiOperation(value = "내 스케줄 조회(트레이너)", notes = "트레이너 본인의 스케줄을 조회합니다.")
    @GetMapping("/trainer")
    public CustomResponse<List<TrainerScheduleResponseDTO>> getSchedules(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {
        return CustomResponse.success(ResponseCode.SUCCESS, trainerScheduleService.getSchedulesByTrainerId(user.getUserId()));
    }

    @ApiOperation(value = "트레이너 스케줄 조회(수강생)", notes = "트레이너 ID로 공개 스케줄을 조회합니다.")
    @ApiImplicitParam(name = "trainerId", value = "트레이너 ID", required = true, dataType = "long", paramType = "path")
    @GetMapping("/trainee/{trainerId}")
    public CustomResponse<List<TrainerScheduleResponseDTO>> getSchedulesByTrainerId(@PathVariable Long trainerId) {
        return CustomResponse.success(ResponseCode.SUCCESS, trainerScheduleService.getSchedulesByTrainerId(trainerId));
    }

    @ApiOperation(value = "스케줄 삭제", notes = "스케줄 ID로 삭제합니다. 예약이 있으면 삭제 불가.")
    @ApiImplicitParam(name = "scheduleId", value = "스케줄 ID", required = true, dataType = "long", paramType = "path")
    @DeleteMapping("/{scheduleId}")
    public CustomResponse<Void> deleteSchedules(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user,
            @PathVariable Long scheduleId) {
        trainerScheduleService.deleteSchedule(scheduleId, user.getUserId());
        return CustomResponse.success(ResponseCode.SUCCESS);
    }
}
