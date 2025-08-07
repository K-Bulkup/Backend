package com.kbulkup.routine.domain;

import com.kbulkup.training.dto.request.TrainerTrainingCreateRequestDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor; // NoArgsConstructor 추가
import lombok.AllArgsConstructor; // AllArgsConstructor 추가

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor // MyBatis 매핑을 위해 기본 생성자 추가
@AllArgsConstructor // Builder를 위해 전체 생성자 추가
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

    public static Routine createRoutine(Long trainingId, TrainerTrainingCreateRequestDTO.RoutineDTO dto, int score) {
        return Routine.builder()
                .trainingId(trainingId)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .routineType(dto.getRoutineType())
                .quizType(dto.getQuizType())
                .orderNumber(dto.getOrderNumber())
                .score(score) // 파라미터로 받은 계산된 score 사용
                .videoUrl(dto.getVideoUrl()) // DB 저장 X, video 저장용
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}