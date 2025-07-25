package com.kbulkup.profile.service;

import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;

public interface TrainerProfileService {

    TrainerProfileDetailResponseDTO getTrainerProfile(Long trainerId);

}
