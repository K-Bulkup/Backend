package com.kbulkup.qna.service;

import com.kbulkup.qna.dto.response.CommonTrainingQnADetailResponseDTO;
import com.kbulkup.qna.dto.response.CommonTrainingQnAListDetailResponseDTO;

import java.util.List;

public interface QnAService {

    void createTraineeTrainingQuestion(Long userId, Long traingId, String question);

    CommonTrainingQnAListDetailResponseDTO getTrainingQnAs(Long trainingId);

    void createTrainerTrainingAnswer(Long qnaId, String answer);
}
