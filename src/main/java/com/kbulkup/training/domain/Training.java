package com.kbulkup.training.domain;

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

    public static Training from(Long trainerId, TrainerTrainingCreateRequestDTO dto, String thumbnailUrl) {
        return Training.builder()
                .trainerId(trainerId)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .level(dto.getLevel())
                .thumbnailUrl(thumbnailUrl)
                .totalScore(0)
                .approvalStatus("대기")
                .averageRating(0.0f)
                .traineeCount(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
