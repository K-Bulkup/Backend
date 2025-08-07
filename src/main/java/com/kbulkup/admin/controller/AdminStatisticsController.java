package com.kbulkup.admin.controller;

import com.kbulkup.admin.dto.response.AdminUserStatisticsResponseDTO;
import com.kbulkup.admin.dto.response.AdminUserSummaryStatsResponseDTO;
import com.kbulkup.admin.service.statistics.AdminStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/statistics")
public class AdminStatisticsController {

    private final AdminStatisticsService adminStatisticsService;

    @GetMapping("/signups")
    public ResponseEntity<List<AdminUserStatisticsResponseDTO>> getUserSignupsStatistics(
            @RequestParam String period,
            @RequestParam(required = false) String role) { // role 파라미터가 여기에 명확히 정의되어 있습니다.
        List<AdminUserStatisticsResponseDTO> stats = adminStatisticsService.getUserSignupsByPeriod(period, role); // period와 role 두 개의 파라미터를 전달하도록 수정
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/users/summary")
    public ResponseEntity<AdminUserSummaryStatsResponseDTO> getUserSummaryStatistics() {
        AdminUserSummaryStatsResponseDTO summary = adminStatisticsService.getUserSummaryStatistics();
        return ResponseEntity.ok(summary);
    }
}