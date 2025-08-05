package com.kbulkup.training.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.kbulkup.routine.domain.Routine;
import com.kbulkup.training.domain.Training;
import com.kbulkup.training.dto.request.TrainerTrainingCreateRequestDTO;
import com.kbulkup.training.mapper.TrainingMapper;
import com.kbulkup.training.mapper.TrainingRoutineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:/config/application-dev.properties")
public class TrainingServiceImpl implements TrainingService {

    private final TrainingMapper trainingMapper;
    private final TrainingRoutineMapper trainingRoutineMapper;
    private final AmazonS3 amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    @Transactional
    public void createTraining(Long trainerId, TrainerTrainingCreateRequestDTO dto, MultipartFile thumbnail) throws IOException {

        String thumbnailUrl = null;

        if (thumbnail != null && !thumbnail.isEmpty()) {
            String originalFilename = thumbnail.getOriginalFilename();
            String storedFileName = "trainings/" + UUID.randomUUID() + "-" + originalFilename;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(thumbnail.getContentType());
            metadata.setContentLength(thumbnail.getSize());

            amazonS3.putObject(bucket, storedFileName, thumbnail.getInputStream(), metadata);

            thumbnailUrl = amazonS3.getUrl(bucket, storedFileName).toString();
        }

        Training training = Training.from(trainerId, dto, thumbnailUrl);
        trainingMapper.createTraining(training);

        List<TrainerTrainingCreateRequestDTO.RoutineDTO> routines = dto.getRoutines();
        if (routines != null && !routines.isEmpty()) {
            Long newTrainingId = training.getTrainingId();

            for (TrainerTrainingCreateRequestDTO.RoutineDTO routineDto : routines) {
                Routine routine = Routine.createRoutine(newTrainingId, routineDto);
                trainingRoutineMapper.createRoutine(routine);

                if (routine.getVideoUrl() != null && !routine.getVideoUrl().isEmpty()) {
                    trainingRoutineMapper.createRoutineVideo(routine.getRoutineId(), routine.getVideoUrl());
                }
            }
        }
    }
}
