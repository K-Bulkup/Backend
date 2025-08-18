package com.kbulkup.asset.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "외부 자산 응답")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalAssetResponseDTO {

    @ApiModelProperty(value = "핀테크 이용번호", example = "199999999012345678901234")
    private String fintechUseNum;

    @ApiModelProperty(value = "포트폴리오 상세(거래, 스냅샷, 구성)")
    @JsonProperty("portfolio")
    private TraineeAssetDetailResponseDTO traineeAssetDetailResponseDTO;
}
