package com.kbulkup.counseling.service;

import com.kbulkup.chat.repository.ChatMongoRepository;
import com.kbulkup.common.exception.UserException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.counseling.domain.Counseling;
import com.kbulkup.counseling.dto.response.CounselingCreateResponseDTO;
import com.kbulkup.counseling.dto.response.CounselingDetailResponseDTO;
import com.kbulkup.counseling.dto.response.TrainerCounselingListResponseDTO;
import com.kbulkup.counseling.mapper.CounselingMapper;
import com.kbulkup.training.mapper.TrainingMapper;
import com.kbulkup.user.domain.User;
import com.kbulkup.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CounselingServiceImpl implements CounselingService {

    private final ChatMongoRepository chatMongoRepository;
    private final CounselingMapper counselingMapper;
    private final TrainingMapper trainingMapper;
    private final UserMapper userMapper;

    @Override
    public List<TrainerCounselingListResponseDTO> getCounselings(Long userId) {
        List<TrainerCounselingListResponseDTO> counselingList = counselingMapper.findByUserId(userId);

        counselingList.forEach(dto -> {
            int count = (int) chatMongoRepository.countUnreadMessages(dto.getRoomId(), userId.toString());
            dto.setUnreadCount(count);
        });

        return counselingList;
    }

    @Override
    @Transactional
    public CounselingCreateResponseDTO createOrGetCounselingsRoom(Long traineeId, Long trainingId) {

        Long trainerId = trainingMapper.findTrainerByTrainingId(trainingId);
        Counseling existing = counselingMapper.findByTraineeAndTrainer(traineeId, trainerId);

        if (existing != null) {
            return CounselingCreateResponseDTO.toDTO(existing.getRoomId(), false);
        }

        String roomId = "room-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12);

        Counseling counseling = Counseling.createCounseling(traineeId, trainerId, trainingId, roomId);
        counselingMapper.insertCounseling(counseling);

        return CounselingCreateResponseDTO.toDTO(roomId, true);
    }

    @Override
    public CounselingDetailResponseDTO getCounselingDetail(String roomId, Long myUserId) {
        Counseling counseling = counselingMapper.findByRoomId(roomId);
        Long targetId = counseling.getUserId().equals(myUserId) ? counseling.getTrainerId() : counseling.getUserId();

        User user = userMapper.findById(targetId).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        //상대방 정보 조회
        return CounselingDetailResponseDTO.create(user.getUsername(), user.getUserProfileUrl(), counseling.getStatus(), counseling.getExpiresAt());
    }
}
