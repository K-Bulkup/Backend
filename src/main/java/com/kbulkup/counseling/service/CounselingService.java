package com.kbulkup.counseling.service;

import com.kbulkup.counseling.dto.response.CounselingCreateResponseDTO;
import com.kbulkup.counseling.dto.response.TrainerCounselingListResponseDTO;

import java.util.List;

public interface CounselingService {

    List<TrainerCounselingListResponseDTO> getCounselings(Long userId);

    CounselingCreateResponseDTO createOrGetCounselingsRoom(Long traineeId, Long trainingId);
}
