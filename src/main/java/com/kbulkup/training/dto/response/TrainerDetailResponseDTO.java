// src/main/java/com/kbulkup/training/dto/response/TrainerDetailResponseDTO.java
package com.kbulkup.training.dto.response;

import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/** 트레이너 상세 응답(프로필 + 운영 중 트레이닝 목록) */
@ApiModel(description = "트레이너 상세 응답(프로필 + 운영중 트레이닝)")
@Getter
@Builder
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TrainerDetailResponseDTO {

    @ApiModelProperty("트레이너 프로필 요약")
    private TrainerProfileDetailResponseDTO profile;

    @ApiModelProperty("운영중 트레이닝 목록")
    private List<TrainerTrainingSummaryResponseDTO> trainings;

    public static TrainerDetailResponseDTO create(TrainerProfileDetailResponseDTO profile,List<TrainerTrainingSummaryResponseDTO> trainings ){
        return TrainerDetailResponseDTO.builder()
                .profile(profile)
                .trainings(trainings)
                .build();
    }

}
