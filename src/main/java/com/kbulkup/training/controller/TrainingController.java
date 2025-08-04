package com.kbulkup.training.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.training.dto.request.TraineeTrainingDetailRequestDTO;
import com.kbulkup.training.dto.request.TrainerTrainingCreateRequestDTO;
import com.kbulkup.training.dto.request.TrainingSearchListRequestDTO;
import com.kbulkup.routine.dto.TraineeRoutineSummaryResponseDTO;
import com.kbulkup.training.dto.response.TraineeTrainingDetailResponseDTO;
import com.kbulkup.training.dto.response.TraineeTrainingListResponseDTO;
import com.kbulkup.training.dto.response.TrainingSearchListResponseDTO;
import com.kbulkup.training.service.TraineeTrainingService;
import com.kbulkup.training.service.TrainingSearchService;
import com.kbulkup.training.service.TrainingService;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TrainingController {

    private final TrainingService trainingService;
    private final TrainingSearchService trainingSearchService;
    private final TraineeTrainingService traineeTrainingService;

    /** [트레이너] 트레이닝 생성 */
    @PostMapping("/trainer/trainings")
    public ResponseEntity<String> createTraining(
            @AuthenticationPrincipal(expression = "user") User user, // 로그인한 사용자 정보
            @RequestPart("dto") TrainerTrainingCreateRequestDTO dto,       // JSON 데이터 부분
            @RequestPart("thumbnail") MultipartFile thumbnail           // 파일 데이터 부분
    ) throws IOException {

        trainingService.createTraining(user.getUserId(), dto, thumbnail);

        return ResponseEntity.ok("Training created successfully.");
    }

    /** [공용] 트레이닝 검색 */
    @GetMapping("/trainings/search")
    public CustomResponse<List<TrainingSearchListResponseDTO>> searchTrainings(
            @ModelAttribute TrainingSearchListRequestDTO dto) {
        return CustomResponse.success(ResponseCode.SUCCESS, trainingSearchService.searchTrainings(dto));
    }

    /** [수강생] 트레이닝 실행(결제 후) 상세 조회 */
    @GetMapping("/trainee/trainings/running/{trainingId}")
    public CustomResponse<TraineeRoutineSummaryResponseDTO> getRunningTrainingDetail(
            @PathVariable Long trainingId,
            @AuthenticationPrincipal(expression = "user") User user) {

        return CustomResponse.success(
                ResponseCode.SUCCESS,
                traineeTrainingService.getTrainingDetail(trainingId, user.getUserId())
        );
    }
    /** [수강생] 트레이닝 탭 전체 목록 조회 */
    @GetMapping("/trainee/trainings/training")
    public CustomResponse<List<TraineeTrainingListResponseDTO>> getAllTrainings() {
        return CustomResponse.success(
                ResponseCode.SUCCESS,
                traineeTrainingService.getAllApprovedTrainings()
        );
    }
    /** [수강생] 트레이닝 상세 조회 (결제 전) */
    @GetMapping("/trainee/trainings/{trainingId}")
    public CustomResponse<TraineeTrainingDetailResponseDTO> getTrainingDetail(@PathVariable Long trainingId) {
        return CustomResponse.success(
                ResponseCode.SUCCESS,
                traineeTrainingService.getTrainingDetail(new TraineeTrainingDetailRequestDTO(trainingId))
        );
    }

}
