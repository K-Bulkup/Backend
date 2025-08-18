package com.kbulkup.training.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.training.dto.response.TraineeEnrollmentResponseDTO;
import com.kbulkup.training.dto.response.TraineeTrainingStatusResponseDTO;
import com.kbulkup.training.service.TraineeEnrollmentService;
import com.kbulkup.training.service.TraineeTrainingStatusService;
import com.kbulkup.user.domain.User;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/** [수강생] 수강 목록 및 진행률 조회 컨트롤러 */
@Api(tags = "Trainee Training", description = "수강생 수강 목록/진행률 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainee")
public class TraineeTrainingController {

    private final TraineeEnrollmentService traineeEnrollmentService;
    private final TraineeTrainingStatusService traineeTrainingStatusService;

    @ApiOperation(value = "내 수강 목록 조회(진행률 계산 포함)")
    @GetMapping("/enrollments")
    public CustomResponse<List<TraineeEnrollmentResponseDTO>> getEnrollments(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {
        return CustomResponse.success(
                ResponseCode.SUCCESS,
                traineeEnrollmentService.getEnrollments(user.getUserId())
        );
    }

    @ApiOperation(value = "트레이닝 진행 상태 조회")
    @ApiImplicitParam(name = "trainingId", value = "트레이닝 ID", required = true, dataType = "long", paramType = "path")
    @GetMapping("/training/{trainingId}/status")
    public CustomResponse<TraineeTrainingStatusResponseDTO> getTrainingStatus(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user,
            @PathVariable Long trainingId) {
        return CustomResponse.success(ResponseCode.SUCCESS, traineeTrainingStatusService.getTrainingStatus(user.getUserId(), trainingId));
    }
}
