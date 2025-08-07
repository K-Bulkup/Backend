package com.kbulkup.training.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TrainerTrainingCreateRequestDTO {

    private String title;
    private String description;
    private String category;
    private String level;
    private String thumbnailUrl;
    private List<RoutineDTO> routines;

    public void updateThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Getter
    @NoArgsConstructor
    public static class RoutineDTO {
        private String title;
        private String description;
        private String routineType;
        private String quizType;
        private Integer orderNumber;
        private Integer score;
        private String videoUrl;

        public static RoutineDTO of(
                String title,
                String description,
                String routineType,
                String quizType,
                Integer orderNumber,
                Integer score,
                String videoUrl
        ) {
            RoutineDTO dto = new RoutineDTO();
            dto.title = title;
            dto.description = description;
            dto.routineType = routineType;
            dto.quizType = quizType;
            dto.orderNumber = orderNumber;
            dto.score = score;
            dto.videoUrl = videoUrl;
            return dto;
        }
    }

    public static TrainerTrainingCreateRequestDTO of(
            String title,
            String description,
            String category,
            String level,
            List<RoutineDTO> routines
    ) {
        TrainerTrainingCreateRequestDTO dto = new TrainerTrainingCreateRequestDTO();
        dto.title = title;
        dto.description = description;
        dto.category = category;
        dto.level = level;
        dto.routines = routines;
        return dto;
    }
}