package com.kbulkup.admin.dto.response;

import lombok.Getter;

@Getter
public class AdminTrainingResponseDTO {
    private Long trainingId;
    private String trainingName;
    private Long trainerId;
    private String trainerName; // 추가
    private String approvalStatus;
    private int totalStudents;
    private String requestDate; // 추가 (created_at을 매핑)
}
