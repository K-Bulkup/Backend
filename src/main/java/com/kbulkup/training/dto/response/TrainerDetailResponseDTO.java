// src/main/java/com/kbulkup/training/dto/response/TrainerDetailResponseDTO.java
package com.kbulkup.training.dto.response;

import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;
import lombok.*;

import java.util.List;

/**
 * 트레이너 상세 응답(프로필 + 운영 중 트레이닝 목록)
 * 세터/빌더 없이 불변 사용. 서비스에서는 of(profile, trainings) 팩토리를 사용.
 */
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TrainerDetailResponseDTO {

    private TrainerProfileDetailResponseDTO profile;
    private List<TrainerTrainingSummaryResponseDTO> trainings;

    public static TrainerDetailResponseDTO create(TrainerProfileDetailResponseDTO profile, List<TrainerTrainingSummaryResponseDTO> trainings) {

        return TrainerDetailResponseDTO.builder()
                .profile(profile)
                .trainings(trainings)
                .build();

    }
}
