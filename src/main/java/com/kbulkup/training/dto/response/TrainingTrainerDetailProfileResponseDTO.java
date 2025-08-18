package com.kbulkup.training.dto.response;

import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** 트레이너 프로필 요약(트레이니 화면용) */
@ApiModel(description = "트레이너 프로필 요약")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TrainingTrainerDetailProfileResponseDTO {

    @ApiModelProperty("트레이너 닉네임")
    private String name;
    @ApiModelProperty("프로필 이미지 URL")
    private String profileUrl;
    @ApiModelProperty("소개/경력")
    private String description;
    @ApiModelProperty("누적 수강생 수")
    private int traineeCount;
    @ApiModelProperty("평균 별점")
    private double averageRating;

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
