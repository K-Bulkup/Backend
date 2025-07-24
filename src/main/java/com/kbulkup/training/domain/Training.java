package com.kbulkup.training.domain;

import com.kbulkup.training.dto.TrainerTrainingCreateRequestDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Training {
    private Long trainingId;           // PK
    private Long trainerId;            // 트레이너 ID
    private String title;              // 트레이닝명
    private String description;        // 설명
    private Integer price;             // 가격
    private String category;           // enum
    private String level;              // enum
    private String thumbnailUrl;       // 썸네일 URL

    // 시스템에서 자동으로 처리/갱신
    private Integer totalScore;        // 총 리워드
    private String approvalStatus;     // 승인 상태
    private Float averageRating;       // 평균 별점
    private Integer traineeCount;      // 수강생 수

    private LocalDateTime createdAt;   // 생성일
    private LocalDateTime updatedAt;   // 수정일

    public static Training from(Long trainerId, com.kbulkup.training.dto.TrainerTrainingCreateRequestDTO dto) {
        return Training.builder()
                .trainerId(trainerId)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .level(dto.getLevel())
                .thumbnailUrl(dto.getThumbnailUrl())
                .totalScore(0) // 초기값 설정
                .approvalStatus("대기") // 기본 승인 대기 상태
                .averageRating(0.0f)
                .traineeCount(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
