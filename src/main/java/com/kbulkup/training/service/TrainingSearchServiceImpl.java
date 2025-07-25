package com.kbulkup.training.service;

import com.kbulkup.training.dto.request.TrainingSearchListRequestDTO;
import com.kbulkup.training.dto.response.TrainingSearchListResponseDTO;
import com.kbulkup.training.mapper.TrainingSearchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingSearchServiceImpl implements TrainingSearchService {

    private final TrainingSearchMapper trainingSearchMapper;

    @Override
    public List<TrainingSearchListResponseDTO> searchTrainings(TrainingSearchListRequestDTO dto) {
        if (dto.getKeyword() == null || dto.getKeyword().isBlank()) {
            return Collections.emptyList();
        }
        return trainingSearchMapper.searchTrainings(dto.getKeyword());
    }
}
