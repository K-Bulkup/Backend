package com.kbulkup.qna.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonTrainingQnAListDetailResponseDTO {
    private String trainingTitle;
    private List<CommonTrainingQnADetailResponseDTO> trainingQnADetails;

    public static CommonTrainingQnAListDetailResponseDTO create(String trainingTitle, List<CommonTrainingQnADetailResponseDTO> trainingQnADetails) {
        return CommonTrainingQnAListDetailResponseDTO.builder()
                .trainingTitle(trainingTitle)
                .trainingQnADetails(trainingQnADetails)
                .build();
    }
}
