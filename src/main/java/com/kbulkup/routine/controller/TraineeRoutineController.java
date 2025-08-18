package com.kbulkup.routine.controller;

import com.kbulkup.routine.dto.response.TraineeRoutineDetailResponseDTO;
import com.kbulkup.routine.service.RoutineService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Routine", description = "루틴 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainee/routines")
public class TraineeRoutineController {

    private final RoutineService routineService;

    @ApiOperation(value = "루틴 상세 조회", notes = "루틴 상세 정보를 조회합니다.")
    @ApiImplicitParam(name = "routineId", value = "루틴 ID", required = true, dataType = "long", paramType = "path")
    @GetMapping("/{routineId}")
    public TraineeRoutineDetailResponseDTO getRoutineDetail(@PathVariable Long routineId) {
        return routineService.getRoutineDetail(routineId);
    }
}
