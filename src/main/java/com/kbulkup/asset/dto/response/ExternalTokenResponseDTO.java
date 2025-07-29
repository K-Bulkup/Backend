package com.kbulkup.asset.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalTokenResponseDTO {
    private String accessToken;
    private String fintechUseNum;
}
