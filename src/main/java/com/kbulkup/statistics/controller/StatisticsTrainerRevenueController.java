package com.kbulkup.statistics.controller;

import com.kbulkup.common.exception.AuthException;
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.statistics.dto.response.DailyRevenueDTO;
import com.kbulkup.statistics.dto.response.MonthlyRevenueDTO;
import com.kbulkup.statistics.dto.response.StatisticsTrainerRevenueResponseDTO;
import com.kbulkup.statistics.service.StatisticsTrainerRevenueService;
import com.kbulkup.user.domain.User;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "Trainer Revenue", description = "트레이너 매출 통계 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer/mypage/report")
public class StatisticsTrainerRevenueController {

    private final StatisticsTrainerRevenueService statisticsTrainerRevenueService;

    @ApiOperation(value = "매출 통계 요약")
    @ApiImplicitParam(name = "trainingId", value = "필터용 트레이닝 ID", required = false, dataType = "long", paramType = "query")
    @GetMapping
    public CustomResponse<StatisticsTrainerRevenueResponseDTO> getRevenueStatistics(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user,
            @RequestParam(required = false) Long trainingId) {

        if (user == null || user.getRoles() == null || !user.getRoles().contains("TRAINER")) {
            throw new AuthException(ResponseCode.TRAINER_ACCESS_DENIED);
        }
        Long trainerId = user.getUserId();
        return CustomResponse.success(
                ResponseCode.SUCCESS,
                statisticsTrainerRevenueService.getTrainerRevenueStatistics(trainerId, trainingId)
        );
    }

    @ApiOperation(value = "일별 매출(최근 30일)")
    @GetMapping("/daily")
    public CustomResponse<List<DailyRevenueDTO>> getDailyRevenue(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {

        if (user == null || user.getRoles() == null || !user.getRoles().contains("TRAINER")) {
            throw new AuthException(ResponseCode.TRAINER_ACCESS_DENIED);
        }
        Long trainerId = user.getUserId();
        return CustomResponse.success(
                ResponseCode.SUCCESS,
                statisticsTrainerRevenueService.getDailyRevenueLast30Days(trainerId)
        );
    }

    @ApiOperation(value = "월별 매출")
    @GetMapping("/monthly")
    public CustomResponse<List<MonthlyRevenueDTO>> getMonthlyRevenue(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {

        if (user == null || user.getRoles() == null || !user.getRoles().contains("TRAINER")) {
            throw new AuthException(ResponseCode.TRAINER_ACCESS_DENIED);
        }
        Long trainerId = user.getUserId();
        return CustomResponse.success(
                ResponseCode.SUCCESS,
                statisticsTrainerRevenueService.getMonthlyRevenue(trainerId)
        );
    }

    @ApiOperation(value = "일별 매출(특정 트레이닝)")
    @ApiImplicitParam(name = "trainingId", value = "트레이닝 ID", required = true, dataType = "long", paramType = "path")
    @GetMapping("/{trainingId}/daily")
    public CustomResponse<List<DailyRevenueDTO>> getDailyRevenueForTraining(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user,
            @PathVariable Long trainingId) {

        if (user == null || user.getRoles() == null || !user.getRoles().contains("TRAINER")) {
            throw new AuthException(ResponseCode.TRAINER_ACCESS_DENIED);
        }
        Long trainerId = user.getUserId();
        return CustomResponse.success(
                ResponseCode.SUCCESS,
                statisticsTrainerRevenueService.getDailyRevenueForTraining(trainerId, trainingId)
        );
    }

    @ApiOperation(value = "월별 매출(특정 트레이닝)")
    @ApiImplicitParam(name = "trainingId", value = "트레이닝 ID", required = true, dataType = "long", paramType = "path")
    @GetMapping("/{trainingId}/monthly")
    public CustomResponse<List<MonthlyRevenueDTO>> getMonthlyRevenueForTraining(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user,
            @PathVariable Long trainingId) {

        if (user == null || user.getRoles() == null || !user.getRoles().contains("TRAINER")) {
            throw new AuthException(ResponseCode.TRAINER_ACCESS_DENIED);
        }
        Long trainerId = user.getUserId();
        return CustomResponse.success(
                ResponseCode.SUCCESS,
                statisticsTrainerRevenueService.getMonthlyRevenueForTraining(trainerId, trainingId)
        );
    }
}
