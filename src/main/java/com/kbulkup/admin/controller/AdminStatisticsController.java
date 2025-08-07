package com.kbulkup.admin.controller;

import com.kbulkup.admin.dto.response.AdminUserStatisticsResponseDTO;
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
    public ResponseEntity<List<AdminUserStatisticsResponseDTO>> getUserSignupsStatistics(@RequestParam String period) {
        List<AdminUserStatisticsResponseDTO> stats = adminStatisticsService.getUserSignupsByPeriod(period);
        return ResponseEntity.ok(stats);
    }
}
