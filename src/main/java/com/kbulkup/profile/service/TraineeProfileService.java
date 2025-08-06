package com.kbulkup.profile.service;


import com.kbulkup.profile.dto.response.TraineeProfileDetailResponseDTO;

public interface TraineeProfileService {

    TraineeProfileDetailResponseDTO getTraineeProfile(Long trainerId);

}
