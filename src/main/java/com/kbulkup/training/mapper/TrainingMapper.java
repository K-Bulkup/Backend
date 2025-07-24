package com.kbulkup.training.mapper;

import com.kbulkup.training.domain.Training;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TrainingMapper {
    void create(Training training);
}
