package com.kbulkup.qna.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.qna.dto.request.TraineeTrainingQnARequestDTO;
import com.kbulkup.qna.dto.request.TrainerTrainingQnARequestDTO;
import com.kbulkup.qna.dto.response.CommonTrainingQnADetailResponseDTO;
import com.kbulkup.qna.dto.response.CommonTrainingQnAListDetailResponseDTO;
import com.kbulkup.qna.service.QnAService;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QnAController {

    private final QnAService qnAService;

    @GetMapping("/common/trainings/{trainingId}/qnas")
    public CustomResponse<CommonTrainingQnAListDetailResponseDTO> getTrainingQnAs(@PathVariable Long trainingId) {
        return CustomResponse.success(ResponseCode.SUCCESS, qnAService.getTrainingQnAs(trainingId));
    }

    @PostMapping("/trainee/trainings/{trainingId}/question")
    public CustomResponse<Void> createTrainingQuestion(@AuthenticationPrincipal(expression = "user") User user, @PathVariable Long trainingId, @RequestBody TraineeTrainingQnARequestDTO dto) {
        qnAService.createTraineeTrainingQuestion(user.getUserId(), trainingId, dto.getQuestionTitle(), dto.getQuestion());
        return CustomResponse.success(ResponseCode.SUCCESS);
    }

    @PostMapping("/trainer/trainings/{trainingId}/answer")
    public CustomResponse<Void> createTrainingAnswer(@RequestBody TrainerTrainingQnARequestDTO dto) {
        qnAService.createTrainerTrainingAnswer(dto.getQnaId(), dto.getAnswer());
        return CustomResponse.success(ResponseCode.SUCCESS);
    }
}
