package com.kbulkup.qna.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.qna.dto.request.TraineeTrainingQnARequestDTO;
import com.kbulkup.qna.dto.request.TrainerTrainingQnARequestDTO;
import com.kbulkup.qna.dto.response.CommonTrainingQnAListDetailResponseDTO;
import com.kbulkup.qna.dto.response.TrainerTrainingListDetailResponseDTO;
import com.kbulkup.qna.service.QnAService;
import com.kbulkup.user.domain.User;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "QnA", description = "트레이닝 QnA 조회/작성 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QnAController {

    private final QnAService qnAService;

    @ApiOperation(value = "내 트레이닝 QnA(트레이너)", notes = "트레이너가 담당 중인 트레이닝별 QnA 개요를 조회합니다.")
    @GetMapping("/trainer/trainings/qnas")
    public CustomResponse<List<TrainerTrainingListDetailResponseDTO>> getTrainerTrainings(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {
        return CustomResponse.success(ResponseCode.SUCCESS, qnAService.getTrainerTrainings(user.getUserId()));
    }

    @ApiOperation(value = "트레이닝 QnA 목록", notes = "트레이닝 ID로 QnA 목록을 조회합니다.")
    @ApiImplicitParam(name = "trainingId", value = "트레이닝 ID", required = true, dataType = "long", paramType = "path")
    @GetMapping("/common/trainings/{trainingId}/qnas")
    public CustomResponse<CommonTrainingQnAListDetailResponseDTO> getTrainingQnAs(@PathVariable Long trainingId) {
        return CustomResponse.success(ResponseCode.SUCCESS, qnAService.getTrainingQnAs(trainingId));
    }

    @ApiOperation(value = "질문 등록(수강생)", notes = "트레이닝에 대한 질문을 등록합니다.")
    @ApiImplicitParam(name = "trainingId", value = "트레이닝 ID", required = true, dataType = "long", paramType = "path")
    @PostMapping("/trainee/trainings/{trainingId}/question")
    public CustomResponse<Void> createTrainingQuestion(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user,
            @PathVariable Long trainingId,
            @ApiParam(value = "질문 요청 바디", required = true) @RequestBody TraineeTrainingQnARequestDTO dto) {
        qnAService.createTraineeTrainingQuestion(user.getUserId(), trainingId, dto.getQuestionTitle(), dto.getQuestion());
        return CustomResponse.success(ResponseCode.SUCCESS);
    }

    @ApiOperation(value = "답변 등록(트레이너)", notes = "QnA ID에 대해 답변을 등록합니다.")
    @PostMapping("/trainer/trainings/{trainingId}/answer")
    public CustomResponse<Void> createTrainingAnswer(
            @ApiParam(value = "답변 요청 바디", required = true) @RequestBody TrainerTrainingQnARequestDTO dto) {
        qnAService.createTrainerTrainingAnswer(dto.getQnaId(), dto.getAnswer());
        return CustomResponse.success(ResponseCode.SUCCESS);
    }
}
