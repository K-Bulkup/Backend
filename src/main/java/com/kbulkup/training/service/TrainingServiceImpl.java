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

        // 썸네일 파일이 존재하면 S3에 업로드
        if (thumbnail != null && !thumbnail.isEmpty()) {
            // S3에 저장될 파일의 고유한 이름 생성
            String originalFilename = thumbnail.getOriginalFilename();
            String storedFileName = "trainings/" + UUID.randomUUID() + "-" + originalFilename;

            // 파일 메타데이터 설정
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(thumbnail.getContentType());
            metadata.setContentLength(thumbnail.getSize());

            // S3에 파일 업로드 실행
            amazonS3.putObject(bucket, storedFileName, thumbnail.getInputStream(), metadata);

            // 업로드된 파일의 전체 URL을 가져옴
            thumbnailUrl = amazonS3.getUrl(bucket, storedFileName).toString();
        }

        // 업로드된 파일 URL을 DTO에 설정
        dto.setThumbnailUrl(thumbnailUrl);

        // DB에 트레이닝 정보 저장
        Training training = Training.from(trainerId, dto);
        trainingMapper.createTraining(training);

        // DB에 루틴 정보 저장
        List<TrainerTrainingCreateRequestDTO.RoutineDTO> routines = dto.getRoutines();
        if (routines != null && !routines.isEmpty()) {
            // 새로 생성된 트레이닝의 ID를 가져옴
            Long newTrainingId = training.getTrainingId();

            for (TrainerTrainingCreateRequestDTO.RoutineDTO routineDto : routines) {
                Routine routine = Routine.createRoutine(newTrainingId, routineDto);
                trainingRoutineMapper.createRoutine(routine);

                // 비디오 URL이 있으면 비디오 정보도 저장
                if (routine.getVideoUrl() != null && !routine.getVideoUrl().isEmpty()) {
                    trainingRoutineMapper.createRoutineVideo(routine.getRoutineId(), routine.getVideoUrl());
                }
            }
        }
    }
}