package com.kbulkup.training.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerTrainingSummaryResponseDTO {
    private Long trainingId;
    private String title;
    private String level;
    private String thumbnailUrl;

    // 추가 필드
    private Long price;
    private Double averageRating;
}
