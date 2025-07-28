package com.kbulkup.counseling.dto.request;

import lombok.Getter;

@Getter
public class CounselingCreateRequestDTO {

    private Long traineeId;
    private Long trainerId;
    private Long trainingId;
}
