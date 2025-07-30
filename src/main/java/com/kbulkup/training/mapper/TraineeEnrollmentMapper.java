package com.kbulkup.training.mapper;

import com.kbulkup.training.dto.response.TraineeEnrollmentResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * [수강생] 수강 목록 Mapper
 * - DTO 직접 매핑 방식
 */
@Mapper
public interface TraineeEnrollmentMapper {

    /**
     * 수강생 수강 목록 조회
     */
    List<TraineeEnrollmentResponseDTO> findTraineeEnrollments(Long userId);

    /**
     * 전체 루틴 수 조회
     */
    int countTotalRoutines(Long trainingId);

    /**
     * 완료된 루틴 수 조회
     */
    int countCompletedRoutines(@Param("trainingId") Long trainingId, @Param("userId") Long userId);

    /**
     * 진행률 업데이트
     */
    void updateTrainingProgress(@Param("trainingId") Long trainingId, @Param("userId") Long userId, @Param("progress") float progress);
}
