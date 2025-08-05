package com.kbulkup.routine.domain;

import com.kbulkup.training.dto.request.TrainerTrainingCreateRequestDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Routine {
    private Long routineId;
    private Long trainingId;
    private String title;
    private String description;
    private String routineType;
    private String quizType;
    private Integer orderNumber;
    private Integer score;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String videoUrl;

    public static Routine createRoutine(Long trainingId, TrainerTrainingCreateRequestDTO.RoutineDTO dto) {
        return Routine.builder()
                .trainingId(trainingId)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .routineType(dto.getRoutineType())
                .quizType(dto.getQuizType())
                .orderNumber(dto.getOrderNumber())
                .score(dto.getScore())
                .videoUrl(dto.getVideoUrl()) // DB 저장 X, video 저장용
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
