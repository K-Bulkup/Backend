package com.kbulkup.routine.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@ApiModel(description = "수강생 트레이닝 요약 응답")
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class TraineeRoutineSummaryResponseDTO {

    @ApiModelProperty(value = "트레이닝 제목")
    private String title;

    @ApiModelProperty(value = "트레이닝 설명")
    private String description;

    @ApiModelProperty(value = "가격")
    private int price;

    @ApiModelProperty(value = "카테고리")
    private String category;

    @ApiModelProperty(value = "난이도")
    private String level;

    @ApiModelProperty(value = "총 점수")
    private int totalScore;

    @ApiModelProperty(value = "평균 평점")
    private float averageRating;

    @ApiModelProperty(value = "수강생 수")
    private int traineeCount;

    @ApiModelProperty(value = "진행률(0~100)")
    private float progress;

    // 트레이너 정보
    @ApiModelProperty(value = "트레이너 닉네임")
    private String trainerNickname;

    @ApiModelProperty(value = "트레이너 프로필 URL")
    private String trainerProfileUrl;

    @ApiModelProperty(value = "트레이너 ID")
    private Long trainerId;

    @ApiModelProperty(value = "수강 등록 ID")
    private Long enrollmentId;

    @ApiModelProperty(value = "루틴 타입별 목록(Map: 타입 → 루틴 리스트)")
    private Map<String, List<RoutineSummaryResponseDTO>> routines;

    @ApiModel(description = "수강생용 루틴 요약(내부 클래스)")
    @Getter
    @AllArgsConstructor(staticName = "of")
    public static class RoutineSummaryResponseDTO {
        @ApiModelProperty(value = "루틴 ID")
        private Long routineId;
        @ApiModelProperty(value = "제목")
        private String title;
        @ApiModelProperty(value = "완료 여부")
        private boolean completed;
        @ApiModelProperty(value = "보상 포인트")
        private int rewardPoint;
        @ApiModelProperty(value = "완료 시각")
        private LocalDateTime completedAt;
        @ApiModelProperty(value = "루틴 타입")
        private String routineType;
        @ApiModelProperty(value = "퀴즈 타입")
        private String quizType;
    }
}
