package com.kbulkup.qna.service;

import com.kbulkup.qna.dto.response.CommonTrainingQnADetailResponseDTO;
import com.kbulkup.qna.dto.response.CommonTrainingQnAListDetailResponseDTO;
import com.kbulkup.qna.dto.response.TrainerTrainingListDetailResponseDTO;

import java.util.List;

public interface QnAService {

    List<TrainerTrainingListDetailResponseDTO> getTrainerTrainings(Long trainerId);

    void createTraineeTrainingQuestion(Long userId, Long traingId, String questionTitle, String question);

    CommonTrainingQnAListDetailResponseDTO getTrainingQnAs(Long trainingId);

    void createTrainerTrainingAnswer(Long qnaId, String answer);
}
