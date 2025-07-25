package com.kbulkup.profile.service;

import com.kbulkup.common.exception.ProfileException;
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.profile.dto.request.TrainerProfileCareerUpdateRequestDTO;
import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;
import com.kbulkup.profile.mapper.TrainerProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrainerProfileServiceImpl implements TrainerProfileService {

    private final TrainerProfileMapper trainerProfileMapper;

    @Override
    @Transactional
    public TrainerProfileDetailResponseDTO getTrainerProfile(Long trainerId) {
        return trainerProfileMapper.getTrainerProfile(trainerId).orElseThrow(() -> new ProfileException(ResponseCode.NOT_FOUND_TRAINER_PROFILE));
    }

    @Override
    @Transactional
    public CustomResponse<Void> updateTrainerProfileCareer(Long trainerId, TrainerProfileCareerUpdateRequestDTO dto) {

        // 트레이너 프로필이 존재하는지 확인
        trainerProfileMapper.getTrainerProfile(trainerId)
                .orElseThrow(() -> new ProfileException(ResponseCode.NOT_FOUND_TRAINER_PROFILE));

        boolean isUpdated = trainerProfileMapper.updateTrainerCareer(trainerId, dto.getCareer());

        if (isUpdated) {
            return CustomResponse.success(ResponseCode.SUCCESS);

        }
        else{
            throw new ProfileException(ResponseCode.TRAINER_CAREER_UPDATE_FAILED);
        }
    }
}
