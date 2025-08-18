package com.kbulkup.training.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/** [수강생] 수강 목록 응답 DTO */
@ApiModel(description = "수강 목록 항목")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TraineeEnrollmentResponseDTO {
    @ApiModelProperty("트레이닝 ID")
    private Long trainingId;
    @ApiModelProperty("제목")
    private String title;
    @ApiModelProperty("썸네일 URL")
    private String thumbnailUrl;
    @ApiModelProperty("진행률(0~100)")
    private float progress;
    @ApiModelProperty("등록 일시")
    private LocalDateTime createdAt;

    public static TraineeEnrollmentResponseDTO of(Long trainingId, String title, String thumbnailUrl, float progress, LocalDateTime createdAt) {
        return new TraineeEnrollmentResponseDTO(trainingId, title, thumbnailUrl, progress, createdAt);
    }
}
