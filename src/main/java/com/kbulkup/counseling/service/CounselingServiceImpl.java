package com.kbulkup.counseling.service;

import com.kbulkup.counseling.domain.Counseling;
import com.kbulkup.counseling.dto.response.CounselingCreateResponseDTO;
import com.kbulkup.counseling.dto.response.TrainerCounselingListResponseDTO;
import com.kbulkup.counseling.mapper.CounselingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CounselingServiceImpl implements CounselingService {

    private final CounselingMapper counselingMapper;

    @Override
    public List<TrainerCounselingListResponseDTO> getCounselingsByTrainer(Long trainerId) {
        return counselingMapper.findByTrainerId(trainerId);
    }

    @Override
    public CounselingCreateResponseDTO createOrGetCounselingsRoom(Long trainerId, Long traineeId, Long trainingId) {
        //trainer, trainee, training 존재 여부 처리 추가 예정

        Counseling existing = counselingMapper.findByTraineeAndTrainer(traineeId, trainerId);

        if (existing != null) {
            return CounselingCreateResponseDTO.toDTO(existing.getRoomId(), false);
        }

        String roomId = "room-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12);

        Counseling counseling = Counseling.createCounseling(traineeId, trainerId, trainingId, roomId);
        counselingMapper.insertCounselings(counseling);

        return CounselingCreateResponseDTO.toDTO(roomId, true);
    }
}
