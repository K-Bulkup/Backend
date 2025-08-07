package com.kbulkup.routine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TraineeRoutineDetailResponseDTO {

    private String routineTitle;         // 루틴 제목
    private String routineDescription;   // 루틴 설명
    private int routineScore;            // 루틴 점수
    private String category;             // 트레이닝 카테고리
    private String level;                // 트레이닝 난이도
    private String routineVideoUrl;      // 루틴 영상 URL
}
