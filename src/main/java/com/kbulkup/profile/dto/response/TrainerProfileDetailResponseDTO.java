package com.kbulkup.profile.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@ApiModel(description = "트레이너 프로필 상세 응답")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainerProfileDetailResponseDTO {

    @ApiModelProperty(value = "닉네임")
    private String username;

    @ApiModelProperty(value = "프로필 이미지 URL")
    private String userProfileUrl;

    @ApiModelProperty(value = "경력 소개")
    private String career;

    @ApiModelProperty(value = "총 평점")
    private double totalAverageRating;

    @ApiModelProperty(value = "누적 수강생 수")
    private int totalTraineeCount;

    @Setter
    @ApiModelProperty(value = "보유 자격증 목록")
    private List<String> certificates;
}
