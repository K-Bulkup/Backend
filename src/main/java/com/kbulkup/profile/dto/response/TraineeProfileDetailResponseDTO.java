package com.kbulkup.profile.dto.response;

import com.kbulkup.user.domain.User;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TraineeProfileDetailResponseDTO {
    private String username;
    private int growthScore;

    public static TraineeProfileDetailResponseDTO toDTO(User user) {
        return TraineeProfileDetailResponseDTO.builder()
                .username(user.getUsername())
                .growthScore(user.getGrowthScore())
                .build();
    }
}
