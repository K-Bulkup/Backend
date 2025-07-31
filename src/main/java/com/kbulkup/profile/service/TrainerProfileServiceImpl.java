package com.kbulkup.profile.service;

import com.kbulkup.certificates.mapper.CertificatesMapper;
import com.kbulkup.common.exception.ProfileException;
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.profile.dto.request.TrainerProfileCareerUpdateRequestDTO;
import com.kbulkup.profile.dto.request.TrainerProfileImageUpdateRequestDTO;
import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;
import com.kbulkup.profile.mapper.TrainerProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainerProfileServiceImpl implements TrainerProfileService {

    private final TrainerProfileMapper trainerProfileMapper;
    private final CertificatesMapper certificatesMapper;

    @Override
    public TrainerProfileDetailResponseDTO getTrainerProfile(Long trainerId) {

        TrainerProfileDetailResponseDTO trainerProfileDetailResponseDTO = trainerProfileMapper.findByTrainerId(trainerId)
                .orElseThrow(() -> new ProfileException(ResponseCode.NOT_FOUND_TRAINER_PROFILE));

        trainerProfileDetailResponseDTO.setCertificates(certificatesMapper.findByTrainerId(trainerId));

        return trainerProfileDetailResponseDTO;

    }

    @Override
    @Transactional
    public CustomResponse<Void> updateTrainerProfileCareer(Long trainerId, TrainerProfileCareerUpdateRequestDTO dto) {

        // 트레이너 프로필이 존재하는지 확인
        trainerProfileMapper.findByTrainerId(trainerId)
                .orElseThrow(() -> new ProfileException(ResponseCode.NOT_FOUND_TRAINER_PROFILE));

        boolean isUpdated = trainerProfileMapper.updateTrainerCareer(trainerId, dto.getCareer());

        if (isUpdated) {
            return CustomResponse.success(ResponseCode.SUCCESS);
        } else {
            throw new ProfileException(ResponseCode.TRAINER_CAREER_UPDATE_FAILED);
        }
    }

    @Override
    @Transactional
    public CustomResponse<Void> updateTrainerProfileImage(Long trainerId, TrainerProfileImageUpdateRequestDTO dto) {

        // 트레이너 프로필이 존재하는지 확인
        trainerProfileMapper.findByTrainerId(trainerId)
                .orElseThrow(() -> new ProfileException(ResponseCode.NOT_FOUND_TRAINER_PROFILE));

        boolean isUpdated = trainerProfileMapper.updateTrainerProfileImage(trainerId, dto.getProfileImageUrl());

        if (isUpdated) {
            return CustomResponse.success(ResponseCode.SUCCESS);
        } else {
            throw new ProfileException(ResponseCode.TRAINER_PROFILE_IMAGE_UPDATE_FAILED);
        }
    }

    @Override
    @Transactional
    public void createInitialProfile(Long trainerId) {
        // 이미 프로필이 존재하는지 확인 (이벤트가 중복 발행될 경우를 대비)
        Optional<TrainerProfileDetailResponseDTO> existingProfile = trainerProfileMapper.findByTrainerId(trainerId);
        if (existingProfile.isEmpty()) {
            trainerProfileMapper.insertInitialProfile(trainerId);
        }
    }
}
