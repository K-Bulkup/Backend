package com.kbulkup.qna.service;

import com.kbulkup.common.exception.QnAException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.qna.dto.response.CommonTrainingQnAListResponseDTO;
import com.kbulkup.qna.mapper.QnAMapper;
import com.kbulkup.training.mapper.TrainingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnAServiceImpl implements QnAService {

    private final QnAMapper qnaMapper;
    private final TrainingMapper trainingMapper;

    @Override
    public List<CommonTrainingQnAListResponseDTO> getTrainingQnAs(Long trainingId) {
        return qnaMapper.getQnAList(trainingId);
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
