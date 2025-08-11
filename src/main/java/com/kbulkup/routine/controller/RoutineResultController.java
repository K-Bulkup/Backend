// --- RoutineResultController.java ---
package com.kbulkup.routine.controller;

import com.kbulkup.routine.dto.request.RoutineResultCreateRequestDTO;
import com.kbulkup.routine.dto.response.RoutineResultCreateResponseDTO;
import com.kbulkup.routine.service.RoutineResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/trainee/routines")
@RequiredArgsConstructor
public class RoutineResultController {

    private final RoutineResultService routineResultService;

    @PostMapping(value="/{routineId}/results")
    public ResponseEntity<RoutineResultCreateResponseDTO> submitRoutineResult(
            @PathVariable Long routineId,
            @RequestPart("requestDTO") RoutineResultCreateRequestDTO requestDTO,
            @RequestPart(name = "file", required = false) MultipartFile file
    ) {
        RoutineResultCreateResponseDTO response = routineResultService.submitResult(routineId, requestDTO, file);
        return ResponseEntity.ok(response);
    }
}
