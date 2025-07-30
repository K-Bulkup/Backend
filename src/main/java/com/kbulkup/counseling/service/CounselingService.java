package com.kbulkup.counseling.service;

import com.kbulkup.counseling.dto.response.CounselingCreateResponseDTO;
import com.kbulkup.counseling.dto.response.CounselingDetailResponseDTO;
import com.kbulkup.counseling.dto.response.CounselingListResponseDTO;

import java.util.List;

public interface CounselingService {

    List<CounselingListResponseDTO> getCounselings(Long userId);

    CounselingCreateResponseDTO createOrGetCounselingsRoom(Long traineeId, Long trainingId);

    CounselingDetailResponseDTO getCounselingDetail(String roomId, Long myUserId);
}
