package com.kbulkup.counseling.service;

import com.kbulkup.counseling.dto.response.TrainerCounselingListResponseDTO;

import java.util.List;

public interface CounselingService {

    List<TrainerCounselingListResponseDTO> getCounselingsByTrainer(Long trainerId);
}
