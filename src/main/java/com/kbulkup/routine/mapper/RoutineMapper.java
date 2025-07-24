package com.kbulkup.routine.mapper;

import com.kbulkup.routine.domain.Routine;
import com.kbulkup.training.dto.TrainerTrainingCreateRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoutineMapper {

    // Routine 도메인 객체를 받아 DB에 저장합니다.
    void createRoutine(Routine routine);

    // RoutineVideos 테이블에 비디오 URL 정보를 저장합니다.
    void createRoutineVideo(@Param("routineId") Long routineId,
                            @Param("videoUrl") String videoUrl);
}
