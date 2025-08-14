// --- RoutineResultService.java ---
package com.kbulkup.routine.service;

import com.kbulkup.routine.dto.request.RoutineResultCreateRequestDTO;
import com.kbulkup.routine.dto.response.RoutineResultCreateResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface RoutineResultService {
    RoutineResultCreateResponseDTO submitResult(Long routineId, RoutineResultCreateRequestDTO dto, MultipartFile file);
}
