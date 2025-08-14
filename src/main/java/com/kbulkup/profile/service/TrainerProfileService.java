package com.kbulkup.profile.service;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.profile.dto.request.TrainerProfileCareerUpdateRequestDTO;
import com.kbulkup.profile.dto.request.TrainerProfileImageUpdateRequestDTO;
import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;
import com.kbulkup.profile.dto.response.TrainerProfileImgUrlResponseDTO;

import java.util.Map;

public interface TrainerProfileService {

    TrainerProfileDetailResponseDTO getTrainerProfile(Long trainerId);

    CustomResponse<Void> updateTrainerProfileCareer(Long trainerId, TrainerProfileCareerUpdateRequestDTO dto);

    TrainerProfileImgUrlResponseDTO updateTrainerProfileImage(Long trainerId, TrainerProfileImageUpdateRequestDTO dto);

    void createInitialProfile(Long trainerId);

    // 트레이니 화면용(프로필 + 운영중 트레이닝 목록)
    Map<String, Object> getTrainerDetailForTrainee(Long trainerId);
}
