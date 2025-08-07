package com.kbulkup.training.domain;

import com.kbulkup.training.dto.request.TrainerTrainingCreateRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Training {

    private Long trainingId;
    private Long trainerId;
    private String title;
    private String description;
    private Integer price;
    private String category;
    private String level;
    private String thumbnailUrl;

    private Integer totalScore;
    private String approvalStatus;
    private Float averageRating;
    private Integer traineeCount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Training from(Long trainerId, TrainerTrainingCreateRequestDTO dto, String thumbnailUrl, int price, int totalScore) {
        return Training.builder()
                .trainerId(trainerId)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(price) // 파라미터로 받은 계산된 price 사용
                .category(dto.getCategory())
                .level(dto.getLevel())
                .thumbnailUrl(thumbnailUrl)
                .totalScore(totalScore) // 파라미터로 받은 계산된 totalScore 사용
                .approvalStatus("대기")
                .averageRating(0.0f) // 기존 Float 타입 유지
                .traineeCount(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}