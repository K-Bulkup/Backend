package com.kbulkup.profile.mapper;

import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface TrainerProfileMapper {

    Optional<TrainerProfileDetailResponseDTO> getTrainerProfile(@Param("trainerId") Long TrainerId);
    boolean updateTrainerCareer(@Param("trainerId") Long trainerId, @Param("career") String career);

}
