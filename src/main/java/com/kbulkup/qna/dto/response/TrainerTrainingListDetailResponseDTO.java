package com.kbulkup.qna.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerTrainingListDetailResponseDTO {
    private Long trainingId;
    private String trainingTitle;
}
