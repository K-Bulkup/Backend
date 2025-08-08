package com.kbulkup.statistics.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer/mypage/me/statistics/reviews")
public class StatisticsTrainerReviewController {

    
    @GetMapping
    public CustomResponse<?> getReviewStatistics(@AuthenticationPrincipal(expression = "user") User user) {
        // TODO: Implement review statistics logic
        return CustomResponse.ok(null);
    }
}
