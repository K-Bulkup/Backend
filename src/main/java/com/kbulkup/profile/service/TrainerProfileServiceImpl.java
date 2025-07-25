package com.kbulkup.profile.service;

import com.kbulkup.common.exception.ProfileException;
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;
import com.kbulkup.profile.mapper.TrainerProfileMapper;
import com.kbulkup.routine.dto.TraineeRoutineDetailResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainerProfileServiceImpl implements TrainerProfileService {

    private final TrainerProfileMapper trainerProfileMapper;

    @Override
    @Transactional
    public TrainerProfileDetailResponseDTO getTrainerProfile(Long trainerId) {
        return trainerProfileMapper.getTrainerProfile(trainerId).orElseThrow(() -> new ProfileException(ResponseCode.NOT_FOUND_TRAINER_PROFILE));
    }
}
