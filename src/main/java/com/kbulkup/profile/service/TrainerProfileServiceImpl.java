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
import com.kbulkup.profile.mapper.TrainerProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:/config/application-dev.properties")
public class TrainerProfileServiceImpl implements TrainerProfileService {

    private final TrainerProfileMapper trainerProfileMapper;
    private final CertificatesMapper certificatesMapper;
    private final AmazonS3 amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

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
    public void updateTrainerProfileImage(Long trainerId, TrainerProfileImageUpdateRequestDTO dto) {

        // 트레이너 프로필이 존재하는지 확인
        trainerProfileMapper.findByTrainerId(trainerId)
                .orElseThrow(() -> new ProfileException(ResponseCode.NOT_FOUND_TRAINER_PROFILE));

        try{

            MultipartFile imgFile = dto.getProfileImage();

            //파일 이름
            String profileImgName = UUID.randomUUID() +  imgFile.getOriginalFilename();

            //메타데이터 설정
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(imgFile.getContentType());
            metadata.setContentLength(imgFile.getSize());

            //S3에 파일 업로드 요청 생성
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket,"trainerProfile/"+profileImgName,imgFile.getInputStream(),metadata);
            amazonS3.putObject(putObjectRequest);

            System.out.println(getPublicUrl(profileImgName));
            trainerProfileMapper.updateTrainerProfileImage(trainerId,getPublicUrl(profileImgName));

        }catch (Exception e) {
            throw new ProfileException(ResponseCode.TRAINER_PROFILE_IMAGE_UPDATE_FAILED);
        }
    }

    //이미지 저장 경로
    private String getPublicUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/trainer/profile/%s", bucket, amazonS3.getRegionName(), fileName);
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
