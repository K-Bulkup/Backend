package com.kbulkup.qna.service;

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
    public void createTraineeTrainingQuestion(Long traineeId, Long traingId, String question) {
        Long trainerId = trainingMapper.findTrainerByTrainingId(traingId);
        qnaMapper.insertQuestion(traingId, traineeId, trainerId, question);
    }
}
