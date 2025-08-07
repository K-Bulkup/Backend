package com.kbulkup.training.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainingSearchListRequestDTO {
    private String keyword;
    private Long trainerId;
}
