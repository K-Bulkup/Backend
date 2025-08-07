package com.kbulkup.qna.service;

import com.kbulkup.qna.dto.response.CommonTrainingQnAListResponseDTO;

import java.util.List;

public interface QnAService {

    void createTraineeTrainingQuestion(Long userId, Long traingId, String question);

    List<CommonTrainingQnAListResponseDTO> getTrainingQnAs(Long trainingId);
}
