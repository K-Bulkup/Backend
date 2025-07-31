package com.kbulkup.payment.mapper;

import com.kbulkup.payment.domain.TraineeTrainingPayment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TraineeTrainingPaymentMapper {
    void insertTraineeTrainingPayment(TraineeTrainingPayment payment);
    void enrollUserToTraining(@Param("userId") Long userId, @Param("trainingId") Long trainingId);
}
