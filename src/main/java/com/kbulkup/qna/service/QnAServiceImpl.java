package com.kbulkup.qna.service;

import com.kbulkup.qna.mapper.QnAMapper;
import com.kbulkup.training.mapper.TrainingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QnAServiceImpl implements QnAService {

    private final QnAMapper qnAMapper;
    private final TrainingMapper trainingMapper;

    @Override
    public void createTraineeTrainingQuestion(Long traineeId, Long traingId, String question) {
        Long trainerId = trainingMapper.findTrainerByTrainingId(traingId);
        qnAMapper.insertQuestion(traingId, traineeId, trainerId, question);
    }
}
