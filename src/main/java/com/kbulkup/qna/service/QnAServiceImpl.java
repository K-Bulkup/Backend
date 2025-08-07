package com.kbulkup.qna.service;

import com.kbulkup.common.exception.QnAException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.qna.dto.response.CommonTrainingQnAListDetailResponseDTO;
import com.kbulkup.qna.mapper.QnAMapper;
import com.kbulkup.training.dto.response.TraineeTrainingReviewResponseDTO;
import com.kbulkup.training.mapper.TraineeTrainingMapper;
import com.kbulkup.training.mapper.TrainingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnAServiceImpl implements QnAService {

    private final QnAMapper qnaMapper;
    private final TrainingMapper trainingMapper;
    private final TraineeTrainingMapper traineeTrainingMapper;

    @Override
    public CommonTrainingQnAListDetailResponseDTO getTrainingQnAs(Long trainingId) {
        TraineeTrainingReviewResponseDTO dto = traineeTrainingMapper.findTrainingTitleByTrainingId(trainingId);
        return CommonTrainingQnAListDetailResponseDTO.create(dto.getTitle(), qnaMapper.getQnAList(trainingId));
    }

    @Override
    public void createTraineeTrainingQuestion(Long traineeId, Long trainingId, String question) {
        Long trainerId = trainingMapper.findTrainerByTrainingId(trainingId);
        qnaMapper.insertQnAQuestion(trainingId, traineeId, trainerId, question);
    }

    @Override
    public void createTrainerTrainingAnswer(Long qnaId, String answer) {
        int updated = qnaMapper.updateQnAAnswer(qnaId, answer);
        if (updated == 0) {
            throw new QnAException(ResponseCode.INVALID_QNA_REQUEST);
        }
    }

}
