package com.kbulkup.training.mapper;

import com.kbulkup.training.domain.Training;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TrainingMapper {
    void createTraining(Training training);

    Long findTrainerByTrainingId(@Param("trainingId") Long trainingId);
}
