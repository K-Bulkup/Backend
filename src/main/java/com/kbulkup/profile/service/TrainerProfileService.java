package com.kbulkup.profile.service;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.profile.dto.request.TrainerProfileCareerUpdateRequestDTO;
import com.kbulkup.profile.dto.request.TrainerProfileImageUpdateRequestDTO;
import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;

public interface TrainerProfileService {

    TrainerProfileDetailResponseDTO getTrainerProfile(Long trainerId);
    CustomResponse<Void> updateTrainerProfileCareer(Long trainerId, TrainerProfileCareerUpdateRequestDTO dto);
    CustomResponse<Void> updateTrainerProfileImage(Long trainerId, TrainerProfileImageUpdateRequestDTO dto);

}
