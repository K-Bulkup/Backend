package com.kbulkup.training.service;

import com.kbulkup.training.dto.request.TrainerTrainingCreateRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TrainingService {
    void createTraining(Long trainerId, TrainerTrainingCreateRequestDTO dto, MultipartFile thumbnail) throws IOException;
}
