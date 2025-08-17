package com.kbulkup.admin.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "사용자 요약 통계")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserSummaryStatsResponseDTO {

    @ApiModelProperty(value = "전체 사용자 수", example = "5321")
    private long totalUsers;

    @ApiModelProperty(value = "트레이너 수", example = "221")
    private long totalTrainers;

    @ApiModelProperty(value = "수강생 수", example = "5100")
    private long totalTrainees;
}
