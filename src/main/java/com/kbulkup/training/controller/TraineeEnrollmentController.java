package com.kbulkup.training.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.training.dto.response.TraineeEnrollmentResponseDTO;
import com.kbulkup.training.service.TraineeEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * [수강생] 수강 목록 및 진행률 조회 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainee/enrollments")
public class TraineeEnrollmentController {

    private final TraineeEnrollmentService traineeEnrollmentService;

    /**
     *  수강생의 전체 수강 목록 조회
     * - 진행률 계산 및 DB 업데이트 포함
     * - RequestParam userId 사용 (JWT 추후 리팩토링 가능)
     */
    @GetMapping
    public CustomResponse<List<TraineeEnrollmentResponseDTO>> getEnrollments(@RequestParam Long userId) {
        return CustomResponse.success(
                ResponseCode.SUCCESS,
                traineeEnrollmentService.getEnrollments(userId)
        );
    }
}
