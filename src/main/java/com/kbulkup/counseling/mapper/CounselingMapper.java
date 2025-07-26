package com.kbulkup.counseling.mapper;

import com.kbulkup.counseling.dto.response.TrainerCounselingListResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CounselingMapper {

    List<TrainerCounselingListResponseDTO> findByTrainerId(Long trainerId);
}
