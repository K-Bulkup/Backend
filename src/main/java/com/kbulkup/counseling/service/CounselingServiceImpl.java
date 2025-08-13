package com.kbulkup.counseling.service;

import com.kbulkup.chat.repository.ChatMongoRepository;
import com.kbulkup.common.exception.CounselingException;
import com.kbulkup.common.exception.UserException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.counseling.domain.CounselingReservation;
import com.kbulkup.counseling.dto.response.CounselingCreateResponseDTO;
import com.kbulkup.counseling.dto.response.CounselingDetailResponseDTO;
import com.kbulkup.counseling.dto.response.CounselingListResponseDTO;
import com.kbulkup.counseling.mapper.CounselingReservationMapper;
import com.kbulkup.training.mapper.TrainingMapper;
import com.kbulkup.user.domain.User;
import com.kbulkup.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CounselingServiceImpl implements CounselingService {

    private final ChatMongoRepository chatMongoRepository;
    private final TrainingMapper trainingMapper;
    private final UserMapper userMapper;
    private final CounselingReservationMapper counselingReservationMapper;

    @Override
    public List<CounselingListResponseDTO> getCounselings(Long userId) {
        List<CounselingListResponseDTO> counselingList = counselingReservationMapper.findChatRoomsByUserId(userId);

        counselingList.forEach(dto -> {
            int count = (int) chatMongoRepository.countUnreadMessages(dto.getRoomId(), userId.toString());
            System.out.println("count = " + count);
            dto.setUnreadCount(count);
        });

        return counselingList;
    }

    @Override
    public CounselingDetailResponseDTO getCounselingDetail(String roomId, Long myUserId) {
        CounselingReservation reservation = counselingReservationMapper.findByRoomId(roomId);

        User myUser = userMapper.findById(myUserId).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));

        Long opponentUserId = reservation.getTraineeId().equals(myUserId) ? reservation.getTrainerId() : reservation.getTraineeId();
        User opponentUser = userMapper.findById(opponentUserId).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));

        return CounselingDetailResponseDTO.create(opponentUser.getUsername(), opponentUser.getUserProfileUrl(), myUser.getUserProfileUrl(), reservation.getStatus(), null);
    }

    @Override
    @Transactional
    public CounselingCreateResponseDTO createOrGetCounselingsRoom(Long traineeId, Long trainingId) {

        Long trainerId = trainingMapper.findTrainerByTrainingId(trainingId);
        CounselingReservation existing = counselingReservationMapper.findByTraineeAndTrainer(traineeId, trainerId);

        if (existing != null) {
            return CounselingCreateResponseDTO.toDTO(existing.getRoomId(), false);
        }

        throw new CounselingException(ResponseCode.RESERVATION_NOT_FOUND);
    }
}
