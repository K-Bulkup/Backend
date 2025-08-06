package com.kbulkup.profile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerProfileImgUrlResponseDTO {

    private String profileImgUrl;

    public static TrainerProfileImgUrlResponseDTO create(String profileImgUrl) {
        return TrainerProfileImgUrlResponseDTO.builder()
                .profileImgUrl(profileImgUrl)
                .build();
    }

}
