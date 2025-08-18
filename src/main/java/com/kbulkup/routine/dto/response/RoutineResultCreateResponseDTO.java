package com.kbulkup.routine.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "루틴 결과 생성 응답")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoutineResultCreateResponseDTO {

    @ApiModelProperty(value = "판정 결과", allowableValues = "PASS, FAIL, PENDING")
    private PassFailResult passFailResult;

    public enum PassFailResult {
        PASS, FAIL, PENDING
    }

    public static RoutineResultCreateResponseDTO from(PassFailResult result) {
        return new RoutineResultCreateResponseDTO(result);
    }
}
