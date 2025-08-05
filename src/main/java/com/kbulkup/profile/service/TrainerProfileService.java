package com.kbulkup.profile.service;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.profile.dto.request.TrainerProfileCareerUpdateRequestDTO;
import com.kbulkup.profile.dto.request.TrainerProfileImageUpdateRequestDTO;
import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;
import com.kbulkup.profile.dto.response.TrainerProfileImgUrlResponseDTO;

public interface TrainerProfileService {

    TrainerProfileDetailResponseDTO getTrainerProfile(Long trainerId);
    CustomResponse<Void> updateTrainerProfileCareer(Long trainerId, TrainerProfileCareerUpdateRequestDTO dto);
    TrainerProfileImgUrlResponseDTO updateTrainerProfileImage(Long trainerId, TrainerProfileImageUpdateRequestDTO dto);
    void createInitialProfile(Long trainerId);

}
