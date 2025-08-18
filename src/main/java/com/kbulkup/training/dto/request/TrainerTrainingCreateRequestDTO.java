package com.kbulkup.training.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(description = "트레이닝 생성 요청")
@Getter
@NoArgsConstructor
public class TrainerTrainingCreateRequestDTO {

    @ApiModelProperty(value = "제목", required = true)
    private String title;

    @ApiModelProperty(value = "설명", required = true)
    private String description;

    @ApiModelProperty(value = "카테고리", required = true)
    private String category;

    @ApiModelProperty(value = "난이도", required = true)
    private String level;

    @ApiModelProperty(value = "썸네일 URL(서버에서 업로드 후 셋팅)")
    private String thumbnailUrl;

    @ApiModelProperty(value = "루틴 목록", required = true)
    private List<RoutineDTO> routines;

    public void updateThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @ApiModel(description = "생성용 루틴 항목")
    @Getter
    @NoArgsConstructor
    public static class RoutineDTO {
        @ApiModelProperty(value = "루틴 제목", required = true)
        private String title;
        @ApiModelProperty(value = "루틴 설명", required = true)
        private String description;
        @ApiModelProperty(value = "루틴 타입", required = true)
        private String routineType;
        @ApiModelProperty(value = "퀴즈 타입")
        private String quizType;
        @ApiModelProperty(value = "순서")
        private Integer orderNumber;
        @ApiModelProperty(value = "점수")
        private Integer score;
        @ApiModelProperty(value = "영상 URL")
        private String videoUrl;
        @ApiModelProperty(value = "정답(서버 저장용, 응답 비노출)")
        private String routineAnswer;

        public static RoutineDTO of(
                String title,
                String description,
                String routineType,
                String quizType,
                Integer orderNumber,
                Integer score,
                String videoUrl,
                String routineAnswer
        ) {
            RoutineDTO dto = new RoutineDTO();
            dto.title = title;
            dto.description = description;
            dto.routineType = routineType;
            dto.quizType = quizType;
            dto.orderNumber = orderNumber;
            dto.score = score;
            dto.videoUrl = videoUrl;
            dto.routineAnswer = routineAnswer;
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
