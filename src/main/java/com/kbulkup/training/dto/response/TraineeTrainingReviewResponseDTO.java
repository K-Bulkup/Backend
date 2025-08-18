package com.kbulkup.training.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "리뷰 화면 헤더 응답")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TraineeTrainingReviewResponseDTO {
    @ApiModelProperty("트레이닝 제목")
    private String title;
}
