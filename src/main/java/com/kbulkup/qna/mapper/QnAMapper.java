package com.kbulkup.qna.mapper;

import com.kbulkup.qna.dto.response.CommonTrainingQnAListResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QnAMapper {

    void insertQuestion(
            @Param("trainingId") Long trainingId,
            @Param("traineeId") Long traineeId,
            @Param("trainerId") Long trainerId,
            @Param("question") String question
    );

    List<CommonTrainingQnAListResponseDTO> getQnAList(@Param("trainingId") Long trainingId);
}
