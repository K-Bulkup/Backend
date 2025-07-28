package com.kbulkup.counseling.mapper;

import com.kbulkup.counseling.domain.Counseling;
import com.kbulkup.counseling.dto.response.TrainerCounselingListResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CounselingMapper {

    List<TrainerCounselingListResponseDTO> findByTrainerId(Long trainerId);

    Counseling findByTraineeAndTrainer(@Param("traineeId") Long traineeId, @Param("trainerId") Long trainerId);

    void insertCounselings(Counseling counseling);
}
