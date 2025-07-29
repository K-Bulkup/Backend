package com.kbulkup.profile.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainerProfileDetailResponseDTO {
    private String username; //닉네임
    private String userProfileUrl; //프로필 url
    private String career; //경력소개
    private double totalAverageRating; //트레이너 별점
    private int totalTraineeCount; //누적 수강생수
    @Setter
    private List<String> certificates; //자격증 정보
}
