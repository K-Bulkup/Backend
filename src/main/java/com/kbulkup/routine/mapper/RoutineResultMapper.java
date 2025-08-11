// --- RoutineResultMapper.java ---
package com.kbulkup.routine.mapper;

import com.kbulkup.routine.domain.RoutineResult;
import com.kbulkup.routine.dto.response.RoutineResultCreateResponseDTO.PassFailResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Optional;

@Mapper
public interface RoutineResultMapper {

    Optional<String> findRoutineAnswerByRoutineId(@Param("routineId") Long routineId);

    String findRoutineDescriptionByRoutineId(@Param("routineId") Long routineId);

    int selectRoutineScoreById(@Param("routineId") Long routineId);

    int existsByRoutineAndEnrollment(@Param("routineId") Long routineId, @Param("enrollmentId") Long enrollmentId);

    void insertRoutineResult(RoutineResult result);

    void updateRoutineResult(RoutineResult result);

    void increaseUserScore(@Param("enrollmentId") Long enrollmentId, @Param("score") int score);

    void updateEnrollmentProgress(@Param("enrollmentId") Long enrollmentId);
}
