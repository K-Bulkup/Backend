package com.kbulkup.qna.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.qna.dto.request.TraineeTrainingQnARequestDTO;
import com.kbulkup.qna.service.QnAService;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QnAController {

    private final QnAService qnAService;

    @PostMapping("/trainee/trainings/{trainingId}/question")
    public CustomResponse<Void> createTrainingQuestion(@AuthenticationPrincipal(expression = "user") User user, @PathVariable Long trainingId, @RequestBody TraineeTrainingQnARequestDTO dto) {
        qnAService.createTraineeTrainingQuestion(user.getUserId(), trainingId, dto.getQuestion());
        return CustomResponse.success(ResponseCode.SUCCESS);
    }

}
