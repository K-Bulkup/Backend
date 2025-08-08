package com.kbulkup.statistics.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.statistics.dto.response.StatisticsTrainerRevenueResponseDTO;
import com.kbulkup.statistics.service.StatisticsTrainerRevenueService;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer/mypage/me/statistics/revenue")
public class StatisticsTrainerRevenueController {

    private final StatisticsTrainerRevenueService statisticsTrainerRevenueService;

    @GetMapping
    public CustomResponse<StatisticsTrainerRevenueResponseDTO> getRevenueStatistics(@AuthenticationPrincipal(expression = "user") User user) {
        // 사용자가 TRAINER 역할을 가지고 있는지 확인
        if (user == null || user.getRoles() == null || !user.getRoles().contains("TRAINER")) {
            return CustomResponse.fail(ResponseCode.FORBIDDEN, "접근 권한이 없습니다. 트레이너만 매출 통계를 조회할 수 있습니다.");
        }

        Long trainerId = user.getUserId();

        StatisticsTrainerRevenueResponseDTO response = statisticsTrainerRevenueService.getTrainerRevenueStatistics(trainerId);
        return CustomResponse.ok(response);
    }
}