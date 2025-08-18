package com.kbulkup.training.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/** 트레이너 상세 응답(프로필 + 운영 중 트레이닝 목록) */
@ApiModel(description = "트레이너 상세 응답(프로필 + 운영중 트레이닝)")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TrainerDetailResponseDTO {

    @ApiModelProperty("트레이너 프로필 요약")
    private TrainingTrainerDetailProfileResponseDTO profile;

    @ApiModelProperty("운영중 트레이닝 목록")
    private List<TrainerTrainingSummaryResponseDTO> trainings;

    public static TrainerDetailResponseDTO of(
            TrainingTrainerDetailProfileResponseDTO profile,
            List<TrainerTrainingSummaryResponseDTO> trainings
    ) {
        return new TrainerDetailResponseDTO(profile, trainings);
    }
}
