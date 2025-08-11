package com.kbulkup.training.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TraineeTrainingStatusResponseDTO {

    private boolean hasWrittenReview;
    private boolean chatRoomCreated;

    public static TraineeTrainingStatusResponseDTO create(boolean hasWrittenReview, boolean chatRoomCreated) {
        return TraineeTrainingStatusResponseDTO.builder()
                .hasWrittenReview(hasWrittenReview)
                .chatRoomCreated(chatRoomCreated)
                .build();
    }
}
