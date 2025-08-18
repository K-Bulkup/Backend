package com.kbulkup.training.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore // 컨트롤러 내부에서만 생성해 사용
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TraineeTrainingDetailRequestDTO {
    private Long trainingId;
}
