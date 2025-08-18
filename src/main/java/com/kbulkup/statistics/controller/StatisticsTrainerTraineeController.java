package com.kbulkup.statistics.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore // 문서 비노출
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer/mypage/me/statistics/trainees")
public class StatisticsTrainerTraineeController {

    @GetMapping
    public CustomResponse<?> getTraineeStatistics(@AuthenticationPrincipal(expression = "user") User user) {
        // TODO: Implement trainee statistics logic
        return CustomResponse.success(ResponseCode.SUCCESS);
    }
}
