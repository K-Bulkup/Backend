package com.kbulkup.training.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * [수강생] 수강 목록 응답 DTO
 * - Lombok 사용 (Getter, AllArgsConstructor, NoArgsConstructor)
 * - 팩토리 메서드 유지
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TraineeEnrollmentResponseDTO {

    private Long trainingId;
    private String title;
    private String thumbnailUrl;
    private float progress;
    private LocalDateTime createdAt;

    /**  팩토리 메서드 */
    public static TraineeEnrollmentResponseDTO of(Long trainingId, String title, String thumbnailUrl, float progress, LocalDateTime createdAt) {
        return new TraineeEnrollmentResponseDTO(trainingId, title, thumbnailUrl, progress, createdAt);
    }
}
