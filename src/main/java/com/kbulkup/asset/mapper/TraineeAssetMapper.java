package com.kbulkup.asset.mapper;

import com.kbulkup.asset.domain.Composition;
import com.kbulkup.asset.domain.Snapshot;
import com.kbulkup.asset.domain.Transaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TraineeAssetMapper {

    List<Transaction> getTransactionsByTraineeId(@Param("traineeId") Long TraineeId);

    List<Snapshot> getSnapshotsByTraineeId(@Param("traineeId") Long TraineeId);

    Composition getCompositionsByTraineeId(@Param("traineeId") Long TraineeId);

}
