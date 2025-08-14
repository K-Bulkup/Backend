package com.kbulkup.profile.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kbulkup.certificates.mapper.CertificatesMapper;
import com.kbulkup.common.exception.ProfileException;
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.profile.dto.request.TrainerProfileCareerUpdateRequestDTO;
import com.kbulkup.profile.dto.request.TrainerProfileImageUpdateRequestDTO;
import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;
import com.kbulkup.profile.dto.response.TrainerProfileImgUrlResponseDTO;
import com.kbulkup.profile.mapper.TrainerProfileMapper;
import com.kbulkup.training.mapper.TraineeTrainerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:/application.properties")
public class TrainerProfileServiceImpl implements TrainerProfileService {

    private final TrainerProfileMapper trainerProfileMapper;
    private final CertificatesMapper certificatesMapper;      // 기존 사용 유지
    private final TraineeTrainerMapper traineeTrainerMapper;  // ✅ 추가
    private final AmazonS3 amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public TrainerProfileDetailResponseDTO getTrainerProfile(Long trainerId) {
        TrainerProfileDetailResponseDTO dto = trainerProfileMapper.findByTrainerId(trainerId)
                .orElseThrow(() -> new ProfileException(ResponseCode.NOT_FOUND_TRAINER_PROFILE));

        // 기존 방식 유지: certificatesMapper 사용
        dto.setCertificates(certificatesMapper.findByTrainerId(trainerId));
        return dto;
    }

    // ✅ 트레이니 화면용: 프로필(집계) + 자격증 + 운영중 트레이닝 목록을 조립해 반환
    @Override
    public Map<String, Object> getTrainerDetailForTrainee(Long trainerId) {
        // 프로필(집계 alias가 TrainerProfileDetailResponseDTO와 일치)
        TrainerProfileDetailResponseDTO p = traineeTrainerMapper.selectTrainerProfile(trainerId);
        if (p == null) {
            throw new ProfileException(ResponseCode.NOT_FOUND_TRAINER_PROFILE);
        }

        // 자격증: TraineeTrainerMapper 사용(또는 certificatesMapper 어느 쪽이든 OK)
        List<String> certs = traineeTrainerMapper.selectTrainerCertificates(trainerId);
        p.setCertificates(certs == null ? List.of() : certs);

        // 운영 중 트레이닝(가격/별점 포함)
        var trainings = traineeTrainerMapper.selectTrainerTrainings(trainerId);

        Map<String, Object> trainer = new HashMap<>();
        trainer.put("username", p.getUsername());
        trainer.put("userProfileUrl", p.getUserProfileUrl());
        trainer.put("career", p.getCareer());
        trainer.put("totalAverageRating", p.getTotalAverageRating());
        trainer.put("totalTraineeCount", p.getTotalTraineeCount());
        trainer.put("certificates", p.getCertificates());

        return Map.of("trainer", trainer, "trainings", trainings);
    }

    @Override
    @Transactional
    public CustomResponse<Void> updateTrainerProfileCareer(Long trainerId, TrainerProfileCareerUpdateRequestDTO dto) {
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
    public TrainerProfileImgUrlResponseDTO updateTrainerProfileImage(Long trainerId, TrainerProfileImageUpdateRequestDTO dto) {
        trainerProfileMapper.findByTrainerId(trainerId)
                .orElseThrow(() -> new ProfileException(ResponseCode.NOT_FOUND_TRAINER_PROFILE));

        try {
            MultipartFile imgFile = dto.getProfileImage();
            String profileImgName = UUID.randomUUID() + imgFile.getOriginalFilename();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(imgFile.getContentType());
            metadata.setContentLength(imgFile.getSize());

            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucket, "trainerProfile/" + profileImgName, imgFile.getInputStream(), metadata);
            amazonS3.putObject(putObjectRequest);

            String profileImgUrl = getPublicUrl(profileImgName);
            trainerProfileMapper.updateTrainerProfileImage(trainerId, profileImgUrl);
            return TrainerProfileImgUrlResponseDTO.create(profileImgUrl);

        } catch (NullPointerException e) {
            throw new ProfileException(ResponseCode.TRAINER_PROFILE_NULL_VALUE_ERROR);
        } catch (Exception e) {
            throw new ProfileException(ResponseCode.TRAINER_PROFILE_IMAGE_UPDATE_FAILED);
        }
    }

    private String getPublicUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/trainerProfile/%s", bucket, amazonS3.getRegionName(), fileName);
    }

    @Override
    @Transactional
    public void createInitialProfile(Long trainerId) {
        Optional<TrainerProfileDetailResponseDTO> existingProfile = trainerProfileMapper.findByTrainerId(trainerId);
        if (existingProfile.isEmpty()) {
            trainerProfileMapper.insertInitialProfile(trainerId);
        }
    }
}
