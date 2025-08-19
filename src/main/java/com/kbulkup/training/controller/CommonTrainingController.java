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
import com.kbulkup.training.dto.request.TrainerTrainingDetailRequestDTO;
import com.kbulkup.training.dto.response.TrainerTrainingDetailResponseDTO;
import com.kbulkup.training.service.TraineeTrainingService;
import com.kbulkup.training.service.TrainingSearchService;
import com.kbulkup.training.service.TrainingService;
import com.kbulkup.training.service.TrainerTrainingService;
import com.kbulkup.user.domain.User;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Api(tags = "Training", description = "트레이닝 생성/검색/상세/리뷰 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommonTrainingController {

    private final TrainingService trainingService;
    private final TrainingSearchService trainingSearchService;
    private final TraineeTrainingService traineeTrainingService;
    private final TrainerTrainingService trainerTrainingService;

    /** [트레이너] 트레이닝 생성 */
    @ApiOperation(value = "트레이닝 생성(트레이너)", notes = "멀티파트: dto(JSON) + thumbnail(file)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "thumbnail", value = "썸네일 이미지", required = true, dataType = "file", paramType = "form")
    })
    @PostMapping("/trainer/trainings")
    public ResponseEntity<CustomResponse<Void>> createTraining(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user,
            @ApiParam(value = "트레이닝 생성 DTO(JSON part)", required = true) @RequestPart("dto") TrainerTrainingCreateRequestDTO dto,
            @RequestPart("thumbnail") MultipartFile thumbnail
    ) throws IOException {
        trainingService.createTraining(user.getUserId(), dto, thumbnail);
        return ResponseEntity.ok(CustomResponse.success(ResponseCode.SUCCESS));
    }

    /** [트레이너] 내 트레이닝 목록 + 검색 */
    @ApiOperation(value = "내 트레이닝 목록/검색(트레이너)")
    @GetMapping("/trainer/trainings")
    public CustomResponse<List<TrainingSearchListResponseDTO>> getMyTrainings(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user,
            @ModelAttribute TrainingSearchListRequestDTO dto
    ) {
        dto.setTrainerId(user.getUserId());
        return CustomResponse.success(
                ResponseCode.SUCCESS,
                trainingSearchService.getTrainerTrainingList(dto)
        );
    }

    /** [공용] 트레이닝 검색 */
    @ApiOperation(value = "트레이닝 검색(공용)")
    @GetMapping("/trainings/search")
    public CustomResponse<List<TrainingSearchListResponseDTO>> searchTrainings(
            @ModelAttribute TrainingSearchListRequestDTO dto) {
        return CustomResponse.success(ResponseCode.SUCCESS, trainingSearchService.searchTrainings(dto));
    }

    /** [수강생] 트레이닝 실행(결제 후) 상세 조회 */
    @ApiOperation(value = "실행 중 트레이닝 상세(수강생)")
    @ApiImplicitParam(name = "trainingId", value = "트레이닝 ID", required = true, dataType = "long", paramType = "path")
    @GetMapping("/trainee/trainings/running/{trainingId}")
    public CustomResponse<TraineeRoutineSummaryResponseDTO> getRunningTrainingDetail(
            @PathVariable Long trainingId,
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {
        var request = new TraineeTrainingDetailRequestDTO(trainingId);
        TraineeRoutineSummaryResponseDTO detail =
                (TraineeRoutineSummaryResponseDTO) traineeTrainingService.getTrainingDetail(request, user.getUserId());
        return CustomResponse.success(ResponseCode.SUCCESS, detail);
    }

    /** [수강생] 트레이닝 상세 조회 */
    @ApiOperation(value = "트레이닝 상세(수강생)")
    @ApiImplicitParam(name = "trainingId", value = "트레이닝 ID", required = true, dataType = "long", paramType = "path")
    @GetMapping("/trainee/trainings/{trainingId}")
    public CustomResponse<Object> getTrainingDetail(
            @PathVariable Long trainingId,
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {
        var request = new TraineeTrainingDetailRequestDTO(trainingId);
        return CustomResponse.success(ResponseCode.SUCCESS, traineeTrainingService.getTrainingDetail(request, user.getUserId()));
    }

    /** [수강생] 트레이닝 탭 전체 목록 조회 */
    @ApiOperation(value = "승인된 트레이닝 전체 목록(수강생)")
    @GetMapping("/trainee/trainings/training")
    public CustomResponse<List<com.kbulkup.training.dto.response.TraineeTrainingListResponseDTO>> getAllTrainings(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {
        return CustomResponse.success(ResponseCode.SUCCESS, traineeTrainingService.getAllApprovedTrainings(user.getUserId()));
    }

    /** [수강생] 트레이닝 리뷰 조회 */
    @ApiOperation(value = "트레이닝 리뷰 헤더 조회(수강생)")
    @ApiImplicitParam(name = "trainingId", value = "트레이닝 ID", required = true, dataType = "long", paramType = "path")
    @GetMapping("/trainee/trainings/reviews/{trainingId}")
    public CustomResponse<TraineeTrainingReviewResponseDTO> getTrainingReview(@PathVariable Long trainingId) {
        return CustomResponse.success(ResponseCode.SUCCESS, traineeTrainingService.getTrainingTitle(trainingId));
    }

    /** [수강생] 트레이닝 리뷰 작성 */
    @ApiOperation(value = "트레이닝 리뷰 작성(수강생)")
    @ApiImplicitParam(name = "trainingId", value = "트레이닝 ID", required = true, dataType = "long", paramType = "path")
    @PostMapping("/trainee/trainings/reviews/{trainingId}")
    public CustomResponse<Void> createTrainingReview(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user,
            @PathVariable Long trainingId,
            @ApiParam(value = "리뷰 작성 바디", required = true)
            @RequestBody TraineeTrainingReviewCreateDTO dto) {
        traineeTrainingService.createReview(user.getUserId(), trainingId, dto);
        return CustomResponse.success(ResponseCode.SUCCESS);
    }

    @ApiOperation(value = "트레이닝 상세(공용)")
    @ApiImplicitParam(name = "trainingId", value = "트레이닝 ID", required = true, dataType = "long", paramType = "path")
    @GetMapping("/common/trainings/{trainingId}")
    public CustomResponse<TrainerTrainingDetailResponseDTO> getTrainerTrainingDetail(
            @PathVariable Long trainingId,
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user
    ) {
        var request = TrainerTrainingDetailRequestDTO.of(trainingId, user.getUserId());
        TrainerTrainingDetailResponseDTO detail = trainerTrainingService.getTrainerTrainingDetail(request);
        return CustomResponse.success(ResponseCode.SUCCESS, detail);
    }

    @ApiOperation(value = "트레이닝에 포함된 루틴 목록(트레이너)")
    @ApiImplicitParam(name = "trainingId", value = "트레이닝 ID", required = true, dataType = "long", paramType = "path")
    @GetMapping("/trainer/trainings/{trainingId}/routines")
    public CustomResponse<List<Map<String, String>>> getTrainerTrainingRoutines(@PathVariable Long trainingId) {
        var routines = trainerTrainingService.getTrainerTrainingRoutines(trainingId);
        return CustomResponse.success(ResponseCode.SUCCESS, routines);
    }
}
