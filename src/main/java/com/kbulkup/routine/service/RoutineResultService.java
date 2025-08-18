// --- RoutineResultService.java ---
package com.kbulkup.routine.service;

import com.kbulkup.routine.dto.request.RoutineResultCreateRequestDTO;
import com.kbulkup.routine.dto.response.RoutineResultCreateResponseDTO;
import com.kbulkup.routine.dto.response.UserAnswerDTO;
import org.springframework.web.multipart.MultipartFile;

public interface RoutineResultService {
    RoutineResultCreateResponseDTO submitResult(Long routineId, RoutineResultCreateRequestDTO dto, MultipartFile file);

    UserAnswerDTO getUserAnswer(Long routineId, Long trainingId, Long userId);
}
