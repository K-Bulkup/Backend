package com.kbulkup.qna.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QnAMapper {

    void insertQuestion(
            @Param("trainingId") Long trainingId,
            @Param("traineeId") Long traineeId,
            @Param("trainerId") Long trainerId,
            @Param("question") String question
    );
}
