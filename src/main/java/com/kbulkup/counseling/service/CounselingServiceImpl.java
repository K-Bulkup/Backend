package com.kbulkup.counseling.service;

import com.kbulkup.counseling.domain.Counseling;
import com.kbulkup.counseling.dto.response.CounselingCreateResponseDTO;
import com.kbulkup.counseling.dto.response.TrainerCounselingListResponseDTO;
import com.kbulkup.counseling.mapper.CounselingMapper;
import com.kbulkup.training.mapper.TrainingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CounselingServiceImpl implements CounselingService {

    private final CounselingMapper counselingMapper;
    private final TrainingMapper trainingMapper;

    @Override
    public List<TrainerCounselingListResponseDTO> getCounselings(Long userId) {
        return counselingMapper.findByUserId(userId);
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
}
