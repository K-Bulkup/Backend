package com.kbulkup.training.mapper;

import com.kbulkup.training.dto.response.TrainingSearchListResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TrainingSearchMapper {
    List<TrainingSearchListResponseDTO> searchTrainings(String keyword);
}
