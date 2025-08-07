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

        // 썸네일 S3 업로드
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

        // 레벨에 따른 가격 계산
        int price = calculatePriceByLevel(dto.getLevel());

        // 레벨과 루틴 타입에 따른 총 점수 계산
        int totalScore = calculateTotalScore(dto.getLevel(), dto.getRoutines());

        // Training 엔티티 생성 (계산된 값 포함)
        Training training = Training.from(trainerId, dto, thumbnailUrl, price, totalScore);
        trainingMapper.createTraining(training);

        // 루틴 생성 (각 루틴의 개별 점수 계산 포함)
        List<TrainerTrainingCreateRequestDTO.RoutineDTO> routines = dto.getRoutines();
        if (routines != null && !routines.isEmpty()) {
            Long newTrainingId = training.getTrainingId();

            for (TrainerTrainingCreateRequestDTO.RoutineDTO routineDto : routines) {
                // 개별 루틴의 점수 계산
                int routineScore = getRoutineScore(dto.getLevel(), routineDto.getRoutineType());

                // 개별 점수를 포함하여 Routine 엔티티 생성
                Routine routine = Routine.createRoutine(newTrainingId, routineDto, routineScore);
                trainingRoutineMapper.createRoutine(routine);

                if (routine.getVideoUrl() != null && !routine.getVideoUrl().isEmpty()) {
                    trainingRoutineMapper.createRoutineVideo(routine.getRoutineId(), routine.getVideoUrl());
                }
            }
        }
    }

    // 레벨에 따라 가격을 계산하는 헬퍼 메소드
    private int calculatePriceByLevel(String level) {
        switch (level) {
            case "초급":
                return 5000;
            case "중급":
                return 10000;
            case "고급":
                return 20000;
            default:
                return 0; // 또는 예외 처리
        }
    }


    // 트레이닝의 총 점수를 계산하는 헬퍼 메소드
    private int calculateTotalScore(String level, List<TrainerTrainingCreateRequestDTO.RoutineDTO> routines) {
        if (routines == null || routines.isEmpty()) {
            return 0;
        }
        return routines.stream()
                .mapToInt(routine -> getRoutineScore(level, routine.getRoutineType()))
                .sum();
    }

    // 레벨과 루틴 타입에 따라 개별 루틴의 점수를 반환하는 헬퍼 메소드
    private int getRoutineScore(String level, String routineType) {
        switch (level) {
            case "초급":
                switch (routineType) {
                    case "스트레칭": return 1;
                    case "근력": return 2;
                    case "유산소": return 3;
                    default: return 0;
                }
            case "중급":
                switch (routineType) {
                    case "스트레칭": return 2;
                    case "근력": return 3;
                    case "유산소": return 5;
                    default: return 0;
                }
            case "고급":
                switch (routineType) {
                    case "스트레칭": return 3;
                    case "근력": return 5;
                    case "유산소": return 7;
                    default: return 0;
                }
            default:
                return 0;
        }
    }
}