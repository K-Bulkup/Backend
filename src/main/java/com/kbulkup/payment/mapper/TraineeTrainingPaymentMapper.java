package com.kbulkup.payment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TraineeTrainingPaymentMapper {

    /**
     * 결제 성공 시 수강 등록(enrollments) 테이블에 신규 row 추가
     * @param userId 결제한 사용자 ID
     * @param trainingId 결제한 강의 ID
     */
    void insertEnrollment(@Param("userId") Long userId, @Param("trainingId") Long trainingId);
}
