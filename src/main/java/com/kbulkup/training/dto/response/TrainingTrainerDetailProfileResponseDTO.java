// src/main/java/com/kbulkup/training/dto/response/TrainingTrainerDetailProfileResponseDTO.java
package com.kbulkup.training.dto.response;

import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;
import lombok.*;

import java.security.cert.Certificate;
import java.util.List;

/**
 * 트레이너 프로필 요약(트레이니 화면용)
 * - 세터/빌더 없이 불변 사용
 * - TrainerProfileDetailResponseDTO -> 본 DTO 변환은 from(...) 사용
 */
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TrainingTrainerDetailProfileResponseDTO {

    private String name;          // 트레이너 닉네임
    private String profileUrl;    // 프로필 이미지 URL
    private String description;   // 소개/경력
    private int traineeCount;     // 누적 수강생 수
    private double averageRating; // 평균 별점

    private List<Certificate> certificateList; //자격증 리스트

    private static TrainingTrainerDetailProfileResponseDTO create(TrainingTrainerDetailProfileResponseDTO trainingTrainerDetailProfileResponseDTO){
        return TrainingTrainerDetailProfileResponseDTO.builder()
                .averageRating(trainingTrainerDetailProfileResponseDTO.getAverageRating())
                .name(trainingTrainerDetailProfileResponseDTO.getName())
                .profileUrl(trainingTrainerDetailProfileResponseDTO.getProfileUrl())
                .description(trainingTrainerDetailProfileResponseDTO.getDescription())
                .traineeCount(trainingTrainerDetailProfileResponseDTO.getTraineeCount())
                .certificateList(trainingTrainerDetailProfileResponseDTO.getCertificateList())
                .build();
    }

}
