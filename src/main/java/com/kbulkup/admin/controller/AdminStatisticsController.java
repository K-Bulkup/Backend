package com.kbulkup.admin.controller;

import com.kbulkup.admin.dto.response.AdminUserStatisticsResponseDTO;
import com.kbulkup.admin.dto.response.AdminUserSummaryStatsResponseDTO;
import com.kbulkup.admin.service.statistics.AdminStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Admin Statistics", description = "관리자 통계 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/statistics")
public class AdminStatisticsController {

    private final AdminStatisticsService adminStatisticsService;

    @ApiOperation(
            value = "가입자 추이 통계",
            notes = "기간(period: daily|weekly|monthly)과 역할(role: TRAINER|TRAINEE) 기준으로 가입자 수를 집계합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "period", value = "집계 기간", required = true,
                    dataType = "string", paramType = "query", allowableValues = "daily,weekly,monthly", example = "daily"),
            @ApiImplicitParam(name = "role", value = "대상 역할(선택)",
                    required = false, dataType = "string", paramType = "query",
                    allowableValues = "TRAINER,TRAINEE", example = "TRAINEE")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @GetMapping("/signups")
    public ResponseEntity<List<AdminUserStatisticsResponseDTO>> getUserSignupsStatistics(
            @RequestParam String period,
            @RequestParam(required = false) String role) { // role 파라미터가 여기에 명확히 정의되어 있습니다.
        List<AdminUserStatisticsResponseDTO> stats = adminStatisticsService.getUserSignupsByPeriod(period, role); // period와 role 두 개의 파라미터를 전달하도록 수정
        return ResponseEntity.ok(stats);
    }

    @ApiOperation(
            value = "사용자 요약 통계",
            notes = "전체 사용자/트레이너/수강생 수를 요약해서 제공합니다."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @GetMapping("/users/summary")
    public ResponseEntity<AdminUserSummaryStatsResponseDTO> getUserSummaryStatistics() {
        AdminUserSummaryStatsResponseDTO summary = adminStatisticsService.getUserSummaryStatistics();
        return ResponseEntity.ok(summary);
    }
}
