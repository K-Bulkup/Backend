package com.kbulkup.profile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainerProfileDetailResponseDTO {
    private String username; //닉네임
    private String userProfileUrl; //프로필 url
    private String career; //경력소개
    private double totalAverageRating; //트레이너 별점
    private int totalStudentCount; //누적 수강생수
    //private List<CertificateDto> certificates;
}
