// src/main/java/com/kbulkup/training/dto/response/TrainingTrainerDetailProfileResponseDTO.java
package com.kbulkup.training.dto.response;

import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.security.cert.Certificate;
import java.util.List;

/** 트레이너 프로필 요약(트레이니 화면용) */
@ApiModel(description = "트레이너 프로필 요약")
@Builder
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
    @ApiModelProperty("트레이너 자격증 목록")
    private List<Certificate> certificateList;

}
