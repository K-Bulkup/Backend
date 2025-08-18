// --- RoutineResultMapper.java ---
package com.kbulkup.routine.mapper;

import com.kbulkup.routine.domain.RoutineResult;
import com.kbulkup.routine.dto.response.RoutineResultCreateResponseDTO.PassFailResult;
import com.kbulkup.routine.dto.response.UserAnswerDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Optional;

@Mapper
public interface RoutineResultMapper {

    Optional<String> findRoutineAnswerByRoutineId(@Param("routineId") Long routineId);

    String findQuizTypeByRoutineId(@Param("routineId") Long routineId);

    String findRoutineDescriptionByRoutineId(@Param("routineId") Long routineId);

    int selectRoutineScoreById(@Param("routineId") Long routineId);

    int existsByRoutineAndEnrollment(@Param("routineId") Long routineId, @Param("enrollmentId") Long enrollmentId);

    void insertRoutineResult(RoutineResult result);

    void updateRoutineResult(RoutineResult result);

    void increaseUserScore(@Param("enrollmentId") Long enrollmentId, @Param("score") int score);

    void updateEnrollmentProgress(@Param("enrollmentId") Long enrollmentId);

    Long findEnrollmentId(@Param("userId")Long userId, @Param("trainingId") Long trainingId);

    UserAnswerDTO findAnswer(@Param("enrollmentId") Long enrollmentId,@Param("routineId") Long routineId);
}
