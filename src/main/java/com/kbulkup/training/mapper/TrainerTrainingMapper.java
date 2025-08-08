package com.kbulkup.training.mapper;

import com.kbulkup.training.dto.request.TrainerTrainingDetailRequestDTO;
import com.kbulkup.training.dto.response.RoutineCategoryResponseDTO;
import com.kbulkup.training.dto.response.TrainerTrainingDetailResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TrainerTrainingMapper {
    TrainerTrainingDetailResponseDTO selectTrainerTrainingDetail(TrainerTrainingDetailRequestDTO dto);
    List<RoutineCategoryResponseDTO> selectRoutineRows(Long trainingId);
}
