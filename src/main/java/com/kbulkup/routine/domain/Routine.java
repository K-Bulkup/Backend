package com.kbulkup.routine.domain;

import com.kbulkup.training.dto.TrainerTrainingCreateRequestDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Routine {
    private Long routineId;         // 루틴 ID (PK)
    private Long trainingId;        // 트레이닝 ID (FK)
    private String title;           // 루틴 제목
    private String description;     // 루틴 설명
    private String routineType;     // 루틴 타입
    private String quizType;        // 퀴즈 타입
    private Integer orderNumber;    // 루틴 순서
    private Integer score;          // 루틴 점수
    private String videoUrl;        // 루틴 영상 URL
    private LocalDateTime createdAt; // 생성 일시
    private LocalDateTime updatedAt; // 수정 일시

    public static Routine create(Long trainingId, TrainerTrainingCreateRequestDTO.RoutineDTO dto) {
        return Routine.builder()
                .trainingId(trainingId)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .routineType(dto.getRoutineType())
                .quizType(dto.getQuizType())
                .orderNumber(dto.getOrderNumber())
                .score(dto.getScore())
                .videoUrl(dto.getVideoUrl())
                .createdAt(LocalDateTime.now()) // 생성 일시 자동 추가
                .updatedAt(LocalDateTime.now()) // 수정 일시 자동 추가
                .build();
    }
}
