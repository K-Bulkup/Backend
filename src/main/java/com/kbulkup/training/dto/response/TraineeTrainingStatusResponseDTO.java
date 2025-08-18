package com.kbulkup.training.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "트레이닝 진행 상태 응답")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TraineeTrainingStatusResponseDTO {
    @ApiModelProperty("리뷰 작성 여부") private boolean hasWrittenReview;
    @ApiModelProperty("상담방 생성 여부") private boolean chatRoomCreated;

    public static TraineeTrainingStatusResponseDTO create(boolean hasWrittenReview, boolean chatRoomCreated) {
        return TraineeTrainingStatusResponseDTO.builder()
                .hasWrittenReview(hasWrittenReview)
                .chatRoomCreated(chatRoomCreated)
                .build();
    }
}
