package com.kbulkup.admin.dto;

import lombok.Getter;

@Getter
public class AdminTrainingResponseDto {
    private Long trainingId;
    private String trainingName;
    private Long trainerId;
    private String approvalStatus;
    private int totalStudents;
}
