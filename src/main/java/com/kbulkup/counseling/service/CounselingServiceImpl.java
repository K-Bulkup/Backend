package com.kbulkup.counseling.service;

import com.kbulkup.counseling.dto.response.TrainerCounselingListResponseDTO;
import com.kbulkup.counseling.mapper.CounselingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CounselingServiceImpl implements CounselingService {

    private final CounselingMapper counselingMapper;

    @Override
    public List<TrainerCounselingListResponseDTO> getCounselingsByTrainer(Long trainerId) {
        return counselingMapper.findByTrainerId(trainerId);
    }
}
