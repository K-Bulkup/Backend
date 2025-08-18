package com.kbulkup.training.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore // 컨트롤러 내부에서만 생성해 사용
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TrainerTrainingDetailRequestDTO {
    private Long trainingId;
    private Long trainerId;

    public static TrainerTrainingDetailRequestDTO of(Long trainingId, Long trainerId) {
        return new TrainerTrainingDetailRequestDTO(trainingId, trainerId);
    }
}
