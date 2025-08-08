package com.kbulkup.training.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.routine.dto.response.TraineeRoutineSummaryResponseDTO;
import com.kbulkup.training.dto.request.TraineeTrainingDetailRequestDTO;
import com.kbulkup.training.dto.request.TraineeTrainingReviewCreateDTO;
import com.kbulkup.training.dto.request.TrainerTrainingCreateRequestDTO;
import com.kbulkup.training.dto.request.TrainingSearchListRequestDTO;
import com.kbulkup.training.dto.response.TraineeTrainingListResponseDTO;
import com.kbulkup.training.dto.response.TraineeTrainingReviewResponseDTO;
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
    public ResponseEntity<CustomResponse<Void>> createTraining(
            @AuthenticationPrincipal(expression = "user") User user,
            @RequestPart("dto") TrainerTrainingCreateRequestDTO dto,
            @RequestPart("thumbnail") MultipartFile thumbnail
    ) throws IOException {
        trainingService.createTraining(user.getUserId(), dto, thumbnail);
        return ResponseEntity.ok(CustomResponse.success(ResponseCode.SUCCESS));
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
        var request = new TraineeTrainingDetailRequestDTO(trainingId);
        TraineeRoutineSummaryResponseDTO detail =
                (TraineeRoutineSummaryResponseDTO) traineeTrainingService.getTrainingDetail(request, user.getUserId());
        return CustomResponse.success(ResponseCode.SUCCESS, detail);
    }

    /** [수강생] 트레이닝 상세 조회 (결제 여부 서비스에서 판별) */
    @GetMapping("/trainee/trainings/{trainingId}")
    public CustomResponse<Object> getTrainingDetail(
            @PathVariable Long trainingId,
            @AuthenticationPrincipal(expression = "user") User user) {
        var request = new TraineeTrainingDetailRequestDTO(trainingId);
        return CustomResponse.success(ResponseCode.SUCCESS, traineeTrainingService.getTrainingDetail(request, user.getUserId()));
    }

    /** [수강생] 트레이닝 탭 전체 목록 조회 */
    @GetMapping("/trainee/trainings/training")
    public CustomResponse<List<TraineeTrainingListResponseDTO>> getAllTrainings(
            @AuthenticationPrincipal(expression = "user") User user) {
        return CustomResponse.success(ResponseCode.SUCCESS, traineeTrainingService.getAllApprovedTrainings(user.getUserId()));
    }

    /** [수강생] 트레이닝 리뷰 조회 */
    @GetMapping("/trainee/trainings/reviews/{trainingId}")
    public CustomResponse<TraineeTrainingReviewResponseDTO> getTrainingReview(@PathVariable Long trainingId) {
        return CustomResponse.success(ResponseCode.SUCCESS, traineeTrainingService.getTrainingTitle(trainingId));
    }

    /** [수강생] 트레이닝 리뷰 작성 */
    @PostMapping("/trainee/trainings/reviews/{trainingId}")
    public CustomResponse<Void> createTrainingReview(
            @AuthenticationPrincipal(expression = "user") User user,
            @PathVariable Long trainingId,
            @RequestBody TraineeTrainingReviewCreateDTO dto) {
        traineeTrainingService.createReview(user.getUserId(), trainingId, dto);
        return CustomResponse.success(ResponseCode.SUCCESS);
    }
}
