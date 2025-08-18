package com.kbulkup.asset.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalAssetResponseDTO {
    private String fintechUseNum;

    @JsonProperty("portfolio")
    private TraineeAssetDetailResponseDTO traineeAssetDetailResponseDTO;
}
