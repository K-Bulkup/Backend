package com.kbulkup.qna.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@ApiModel(description = "트레이닝 QnA 목록 응답")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonTrainingQnAListDetailResponseDTO {

    @ApiModelProperty(value = "트레이닝 제목")
    private String trainingTitle;

    @ApiModelProperty(value = "QnA 상세 목록")
    private List<CommonTrainingQnADetailResponseDTO> trainingQnADetails;

    public static CommonTrainingQnAListDetailResponseDTO create(String trainingTitle, List<CommonTrainingQnADetailResponseDTO> trainingQnADetails) {
        return CommonTrainingQnAListDetailResponseDTO.builder()
                .trainingTitle(trainingTitle)
                .trainingQnADetails(trainingQnADetails)
                .build();
    }
}
