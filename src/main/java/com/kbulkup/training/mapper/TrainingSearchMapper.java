package com.kbulkup.training.mapper;

import com.kbulkup.training.dto.response.TrainingSearchListResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TrainingSearchMapper {
    List<TrainingSearchListResponseDTO> searchTrainings(@Param("keyword") String keyword);
}
