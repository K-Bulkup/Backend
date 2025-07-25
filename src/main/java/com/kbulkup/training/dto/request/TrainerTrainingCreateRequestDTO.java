package com.kbulkup.training.dto;

import lombok.Data;

import java.util.List;

@Data
public class TrainerTrainingCreateRequestDTO {
    private String title; // 트레이닝명
    private String description; // 트레이닝 설명
    private Integer price; // 가격
    private String category; // 카테고리
    private String level; // 난이도
    private String thumbnailUrl; // 썸네일URL

    // 루틴 목록
    private List<RoutineDTO> routines;

    @Data
    public static class RoutineDTO {
        private String title; // 루틴 제목
        private String description; // 루틴 설명
        private String routineType; // 루틴 타입
        private String quizType; // 퀴즈 타입
        private Integer orderNumber; // 루틴 순서
        private Integer score;  // 루틴 점수
        private String videoUrl; // 루틴 영상 URL
    }
}
