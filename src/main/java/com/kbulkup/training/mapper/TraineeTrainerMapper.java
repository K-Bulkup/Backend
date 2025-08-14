package com.kbulkup.training.mapper;

import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;
import com.kbulkup.training.dto.response.TrainerTrainingSummaryResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TraineeTrainerMapper {

    // ✅ 프로필은 profile DTO로
    TrainerProfileDetailResponseDTO selectTrainerProfile(@Param("trainerId") Long trainerId);

    List<String> selectTrainerCertificates(@Param("trainerId") Long trainerId);

    List<TrainerTrainingSummaryResponseDTO> selectTrainerTrainings(@Param("trainerId") Long trainerId);
}
