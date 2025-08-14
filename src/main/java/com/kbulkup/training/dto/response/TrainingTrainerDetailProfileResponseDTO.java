// src/main/java/com/kbulkup/training/dto/response/TrainingTrainerDetailProfileResponseDTO.java
package com.kbulkup.training.dto.response;

import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 트레이너 프로필 요약(트레이니 화면용)
 * - 세터/빌더 없이 불변 사용
 * - TrainerProfileDetailResponseDTO -> 본 DTO 변환은 from(...) 사용
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TrainingTrainerDetailProfileResponseDTO {

    private String name;          // 트레이너 닉네임
    private String profileUrl;    // 프로필 이미지 URL
    private String description;   // 소개/경력
    private int traineeCount;     // 누적 수강생 수
    private double averageRating; // 평균 별점

    /** Profile DTO -> 본 DTO 변환 (세터 없이 안전하게 사용) */
    public static TrainingTrainerDetailProfileResponseDTO from(TrainerProfileDetailResponseDTO p) {
        if (p == null) {
            return new TrainingTrainerDetailProfileResponseDTO("", "", "", 0, 0.0);
        }
        return new TrainingTrainerDetailProfileResponseDTO(
                p.getUsername(),
                p.getUserProfileUrl(),
                p.getCareer(),
                p.getTotalTraineeCount(),
                p.getTotalAverageRating()
        );
    }

    /** 명시적 값으로 생성하고 싶을 때 쓰는 팩토리 */
    public static TrainingTrainerDetailProfileResponseDTO of(
            String name,
            String profileUrl,
            String description,
            int traineeCount,
            double averageRating
    ) {
        return new TrainingTrainerDetailProfileResponseDTO(
                name, profileUrl, description, traineeCount, averageRating
        );
    }
}
