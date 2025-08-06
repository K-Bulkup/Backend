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

    void insertPortfolio(@Param("traineeId") Long id);

    void insertTransactions(@Param("traineeId") Long traineeId, @Param("transactions") List<Transaction> transactions);

    void insertSnapshots(@Param("traineeId") Long traineeId, @Param("snapshots") List<Snapshot> snapshots);

    void insertComposition(@Param("traineeId") Long traineeId, @Param("composition") Composition composition);

    Long findUserIdByRoomID(@Param("roomId") String roomId);
}
