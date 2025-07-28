package com.kbulkup.counseling.mapper;

import com.kbulkup.counseling.domain.Counseling;
import com.kbulkup.counseling.dto.response.TrainerCounselingListResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CounselingMapper {

    List<TrainerCounselingListResponseDTO> findByUserId(Long userId);

    Counseling findByTraineeAndTrainer(@Param("traineeId") Long traineeId, @Param("trainerId") Long trainerId);

    Counseling findByRoomId(@Param("roomId") String roomId);

    void insertCounseling(Counseling counseling);

    void updateLatestMessage(@Param("roomId") String roomId, @Param("latestMessage") String latestMessage, @Param("latestAt") LocalDateTime latestAt);
}
