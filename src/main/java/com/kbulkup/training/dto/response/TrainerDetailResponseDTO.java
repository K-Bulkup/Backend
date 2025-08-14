// src/main/java/com/kbulkup/training/dto/response/TrainerDetailResponseDTO.java
package com.kbulkup.training.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 트레이너 상세 응답(프로필 + 운영 중 트레이닝 목록)
 * 세터/빌더 없이 불변 사용. 서비스에서는 of(profile, trainings) 팩토리를 사용.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TrainerDetailResponseDTO {

    private TrainingTrainerDetailProfileResponseDTO profile;
    private List<TrainerTrainingSummaryResponseDTO> trainings;

    public static TrainerDetailResponseDTO of(
            TrainingTrainerDetailProfileResponseDTO profile,
            List<TrainerTrainingSummaryResponseDTO> trainings
    ) {
        return new TrainerDetailResponseDTO(profile, trainings);
    }
}
