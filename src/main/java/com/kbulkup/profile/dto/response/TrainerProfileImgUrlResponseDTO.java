package com.kbulkup.profile.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "프로필 이미지 URL 응답")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerProfileImgUrlResponseDTO {

    @ApiModelProperty(value = "프로필 이미지 URL")
    private String profileImgUrl;

    public static TrainerProfileImgUrlResponseDTO create(String profileImgUrl) {
        return TrainerProfileImgUrlResponseDTO.builder()
                .profileImgUrl(profileImgUrl)
                .build();
    }
}
