package com.kbulkup.training.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TrainerTrainingSummaryDTO {

    private Long trainingId;
    private String title;
    private String level;
    private String thumbnailUrl;
}

