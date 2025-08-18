package com.kbulkup.admin.controller;

import com.kbulkup.admin.dto.response.AdminUserStatisticsResponseDTO;
import com.kbulkup.admin.dto.response.AdminUserSummaryStatsResponseDTO;
import com.kbulkup.admin.service.statistics.AdminStatisticsService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Admin Statistics", description = "관리자 통계 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/statistics")
public class AdminStatisticsController {

    private final AdminStatisticsService adminStatisticsService;

    @ApiOperation(value = "가입자 추이 통계", notes = "기간(period: daily|weekly|monthly)과 역할(role: TRAINER|TRAINEE) 기준으로 가입자 수를 집계합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "period", value = "집계 기간", required = true,
                    dataType = "string", paramType = "query", allowableValues = "daily,weekly,monthly"),
            @ApiImplicitParam(name = "role", value = "대상 역할(선택)",
                    required = false, dataType = "string", paramType = "query", allowableValues = "TRAINER,TRAINEE")
    })
    @GetMapping("/signups")
    public ResponseEntity<List<AdminUserStatisticsResponseDTO>> getUserSignupsStatistics(
            @RequestParam String period,
            @RequestParam(required = false) String role) {
        List<AdminUserStatisticsResponseDTO> stats = adminStatisticsService.getUserSignupsByPeriod(period, role);
        return ResponseEntity.ok(stats);
    }

    @ApiOperation(value = "사용자 요약 통계", notes = "전체 사용자/트레이너/수강생 수를 요약 제공합니다.")
    @GetMapping("/users/summary")
    public ResponseEntity<AdminUserSummaryStatsResponseDTO> getUserSummaryStatistics() {
        AdminUserSummaryStatsResponseDTO summary = adminStatisticsService.getUserSummaryStatistics();
        return ResponseEntity.ok(summary);
    }
}
