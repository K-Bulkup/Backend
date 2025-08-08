// --- RoutineResultService.java ---
package com.kbulkup.routine.service;

import com.kbulkup.routine.dto.request.RoutineResultCreateRequestDTO;
import com.kbulkup.routine.dto.response.RoutineResultCreateResponseDTO;

public interface RoutineResultService {
    RoutineResultCreateResponseDTO submitResult(Long routineId, RoutineResultCreateRequestDTO dto);
}
