package com.kbulkup.counseling.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.counseling.dto.request.TrainerScheduleCreateRequestDTO;
import com.kbulkup.counseling.dto.response.TrainerScheduleResponseDTO;
import com.kbulkup.counseling.service.TrainerScheduleService;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class TrainerScheduleController {

    private final TrainerScheduleService trainerScheduleService;

    @PostMapping("/trainer")
    public CustomResponse<Void> createSchedules(@AuthenticationPrincipal(expression = "user") User user, @RequestBody TrainerScheduleCreateRequestDTO dto) {
        trainerScheduleService.createSchedules(dto, user.getUserId());
        return CustomResponse.success(ResponseCode.SUCCESS);
    }

    @GetMapping("/trainer")
    public CustomResponse<List<TrainerScheduleResponseDTO>> getSchedules(@AuthenticationPrincipal(expression = "user") User user) {
        return CustomResponse.success(ResponseCode.SUCCESS, trainerScheduleService.getSchedulesByTrainerId(user.getUserId()));
    }

    @GetMapping("/trainee/{trainerId}")
    public CustomResponse<List<TrainerScheduleResponseDTO>> getSchedulesByTrainerId(@PathVariable Long trainerId) {
        return CustomResponse.success(ResponseCode.SUCCESS, trainerScheduleService.getSchedulesByTrainerId(trainerId));
    }

    @DeleteMapping("/{scheduleId}")
    public CustomResponse<Void> deleteSchedules(@AuthenticationPrincipal(expression = "user") User user, @PathVariable Long scheduleId) {
        trainerScheduleService.deleteSchedule(scheduleId, user.getUserId());
        return CustomResponse.success(ResponseCode.SUCCESS);
    }
}
