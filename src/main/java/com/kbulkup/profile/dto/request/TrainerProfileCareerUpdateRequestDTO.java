package com.kbulkup.profile.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "트레이너 프로필 경력 수정 요청")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerProfileCareerUpdateRequestDTO {
    @ApiModelProperty(value = "경력/소개")
    private String career;
}
