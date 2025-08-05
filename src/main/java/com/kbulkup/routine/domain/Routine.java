package com.kbulkup.routine.domain;

import com.kbulkup.training.dto.request.TrainerTrainingCreateRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    private String videoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Routine createRoutine(Long trainingId, TrainerTrainingCreateRequestDTO.RoutineDTO dto) {
        return Routine.builder()
                .trainingId(trainingId)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .routineType(dto.getRoutineType())
                .quizType(dto.getQuizType())
                .orderNumber(dto.getOrderNumber())
                .score(dto.getScore())
                .videoUrl(dto.getVideoUrl())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
