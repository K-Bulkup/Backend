package com.kbulkup.statistics.controller;

import com.kbulkup.common.exception.AuthException;
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.statistics.dto.response.DailyRevenueDTO;
import com.kbulkup.statistics.dto.response.MonthlyRevenueDTO;
import com.kbulkup.statistics.dto.response.StatisticsTrainerRevenueResponseDTO;
import com.kbulkup.statistics.service.StatisticsTrainerRevenueService;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer/mypage/report")
public class StatisticsTrainerRevenueController {

    private final StatisticsTrainerRevenueService statisticsTrainerRevenueService;

    @GetMapping
    public CustomResponse<StatisticsTrainerRevenueResponseDTO> getRevenueStatistics(
            @AuthenticationPrincipal(expression = "user") User user,
            @RequestParam(required = false) Long trainingId) {
        // 사용자가 TRAINER 역할을 가지고 있는지 확인
        if (user == null || user.getRoles() == null || !user.getRoles().contains("TRAINER")) {
            throw new AuthException(ResponseCode.TRAINER_ACCESS_DENIED);
        }

        Long trainerId = user.getUserId();

        StatisticsTrainerRevenueResponseDTO response = statisticsTrainerRevenueService.getTrainerRevenueStatistics(trainerId, trainingId);
        return CustomResponse.success(ResponseCode.SUCCESS, response);
    }

    @GetMapping("/daily")
    public CustomResponse<List<DailyRevenueDTO>> getDailyRevenue(@AuthenticationPrincipal(expression = "user") User user) {
        if (user == null || user.getRoles() == null || !user.getRoles().contains("TRAINER")) {
            throw new AuthException(ResponseCode.TRAINER_ACCESS_DENIED);
        }
        Long trainerId = user.getUserId();
        List<DailyRevenueDTO> response = statisticsTrainerRevenueService.getDailyRevenueLast30Days(trainerId);
        return CustomResponse.success(ResponseCode.SUCCESS, response);
    }

    @GetMapping("/monthly")
    public CustomResponse<List<MonthlyRevenueDTO>> getMonthlyRevenue(@AuthenticationPrincipal(expression = "user") User user) {
        if (user == null || user.getRoles() == null || !user.getRoles().contains("TRAINER")) {
            throw new AuthException(ResponseCode.TRAINER_ACCESS_DENIED);
        }
        Long trainerId = user.getUserId();
        List<MonthlyRevenueDTO> response = statisticsTrainerRevenueService.getMonthlyRevenue(trainerId);
        return CustomResponse.success(ResponseCode.SUCCESS, response);
    }

    @GetMapping("/{trainingId}/daily")
    public CustomResponse<List<DailyRevenueDTO>> getDailyRevenueForTraining(
            @AuthenticationPrincipal(expression = "user") User user,
            @PathVariable Long trainingId) {
        if (user == null || user.getRoles() == null || !user.getRoles().contains("TRAINER")) {
            throw new AuthException(ResponseCode.TRAINER_ACCESS_DENIED);
        }
        Long trainerId = user.getUserId();
        List<DailyRevenueDTO> response = statisticsTrainerRevenueService.getDailyRevenueForTraining(trainerId, trainingId);
        return CustomResponse.success(ResponseCode.SUCCESS, response);
    }

    @GetMapping("/{trainingId}/monthly")
    public CustomResponse<List<MonthlyRevenueDTO>> getMonthlyRevenueForTraining(
            @AuthenticationPrincipal(expression = "user") User user,
            @PathVariable Long trainingId) {
        if (user == null || user.getRoles() == null || !user.getRoles().contains("TRAINER")) {
            throw new AuthException(ResponseCode.TRAINER_ACCESS_DENIED);
        }
        Long trainerId = user.getUserId();
        List<MonthlyRevenueDTO> response = statisticsTrainerRevenueService.getMonthlyRevenueForTraining(trainerId, trainingId);
        return CustomResponse.success(ResponseCode.SUCCESS, response);
    }
}