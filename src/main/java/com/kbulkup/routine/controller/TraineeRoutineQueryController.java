package com.kbulkup.routine.controller;

import com.kbulkup.routine.dto.TraineeRoutineDetailResponseDTO;
import com.kbulkup.routine.service.TraineeRoutineQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainee/routines")
public class TraineeRoutineQueryController {

    private final TraineeRoutineQueryService traineeRoutineQueryService;

    /**
     * 루틴 상세 정보 조회 API
     *
     * @param routineId 조회할 루틴 ID
     * @return 루틴 상세 정보 응답 DTO
     */
    @GetMapping("/{routineId}")
    public TraineeRoutineDetailResponseDTO getRoutineDetail(
            @PathVariable Long routineId) {
        return traineeRoutineQueryService.getRoutineDetail(routineId);
    }
}
