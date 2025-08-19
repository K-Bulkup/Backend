// src/main/java/com/kbulkup/training/service/TrainerServiceImpl.java
package com.kbulkup.training.service;

import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;
import com.kbulkup.profile.service.TrainerProfileService;
import com.kbulkup.training.dto.response.TrainerDetailResponseDTO;
import com.kbulkup.training.dto.response.TrainerTrainingSummaryResponseDTO;
import com.kbulkup.training.dto.response.TrainingTrainerDetailProfileResponseDTO;
import com.kbulkup.training.mapper.TraineeTrainerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TraineeTrainerMapper traineeTrainerMapper;
    private final TrainerProfileService trainerProfileService;

    @Override
    public TrainerDetailResponseDTO getTrainerDetail(Long trainerId) {

        // 2) 세터/빌더 없이 변환 (정적 팩토리)
        TrainerProfileDetailResponseDTO profile = trainerProfileService.getTrainerProfile(trainerId);

        // 3) 운영 중 트레이닝 목록
        List<TrainerTrainingSummaryResponseDTO> trainings =
                traineeTrainerMapper.selectTrainerTrainings(trainerId);

        // 4) 응답 조립 (불변 DTO + 팩토리)
        return TrainerDetailResponseDTO.create(profile, trainings);
    }
}
