package com.kbulkup.training.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.training.dto.response.TraineeEnrollmentResponseDTO;
import com.kbulkup.training.dto.response.TraineeTrainingStatusResponseDTO;
import com.kbulkup.training.service.TraineeEnrollmentService;
import com.kbulkup.training.service.TraineeTrainingStatusService;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * [수강생] 수강 목록 및 진행률 조회 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainee")
public class TraineeTrainingController {

    private final TraineeEnrollmentService traineeEnrollmentService;
    private final TraineeTrainingStatusService traineeTrainingStatusService;

    /**
     *  수강생의 전체 수강 목록 조회
     * - 진행률 계산 및 DB 업데이트 포함
     */
    @GetMapping("/enrollments")
    public CustomResponse<List<TraineeEnrollmentResponseDTO>> getEnrollments(@AuthenticationPrincipal(expression = "user") User user) {
        return CustomResponse.success(
                ResponseCode.SUCCESS,
                traineeEnrollmentService.getEnrollments(user.getUserId())
        );
    }

    @GetMapping("/training/{trainingId}/status")
    public CustomResponse<TraineeTrainingStatusResponseDTO> getTrainingStatus(@AuthenticationPrincipal(expression = "user") User user, @PathVariable Long trainingId) {
        return CustomResponse.success(ResponseCode.SUCCESS, traineeTrainingStatusService.getTrainingStatus(user.getUserId(), trainingId));
    }

}
