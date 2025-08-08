// --- RoutineResultController.java ---
package com.kbulkup.routine.controller;

import com.kbulkup.routine.dto.request.RoutineResultCreateRequestDTO;
import com.kbulkup.routine.dto.response.RoutineResultCreateResponseDTO;
import com.kbulkup.routine.service.RoutineResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainee/routines")
@RequiredArgsConstructor
public class RoutineResultController {

    private final RoutineResultService routineResultService;

    @PostMapping("/{routineId}/results")
    public ResponseEntity<RoutineResultCreateResponseDTO> submitRoutineResult(
            @PathVariable Long routineId,
            @RequestBody RoutineResultCreateRequestDTO requestDTO
    ) {
        RoutineResultCreateResponseDTO response = routineResultService.submitResult(routineId, requestDTO);
        return ResponseEntity.ok(response);
    }
}
