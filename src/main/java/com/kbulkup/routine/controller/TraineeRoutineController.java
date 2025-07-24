package com.kbulkup.routine.controller;

import com.kbulkup.routine.dto.TraineeRoutineDetailResponseDTO;
import com.kbulkup.routine.service.RoutineService;
import com.kbulkup.training.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainee/routines")
public class TraineeRoutineController {

    private final RoutineService routineService;

    /**
     * 루틴 상세 정보 조회 API
     *
     * @param routineId 조회할 루틴 ID
     * @return 루틴 상세 정보 응답 DTO
     */
    @GetMapping("/{routineId}")
    public TraineeRoutineDetailResponseDTO getRoutineDetail(
            @PathVariable Long routineId) {
        return routineService.getRoutineDetail(routineId);
    }
}
