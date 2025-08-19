package com.kbulkup.profile.dto.response;

import com.kbulkup.user.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "수강생 프로필 상세 응답")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TraineeProfileDetailResponseDTO {

    @ApiModelProperty(value = "사용자 이름")
    private String username;

    @ApiModelProperty(value = "성장 점수")
    private int growthScore;

    public static TraineeProfileDetailResponseDTO toDTO(User user) {
        return TraineeProfileDetailResponseDTO.builder()
                .username(user.getUsername())
                .growthScore(user.getGrowthScore())
                .build();
    }
}
