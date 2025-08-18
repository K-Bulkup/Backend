package com.kbulkup.routine.controller;

import com.kbulkup.routine.dto.request.RoutineResultCreateRequestDTO;
import com.kbulkup.routine.dto.response.RoutineResultCreateResponseDTO;
import com.kbulkup.routine.service.RoutineResultService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "Routine Result", description = "루틴 결과 제출 API")
@RestController
@RequestMapping("/api/trainee/routines")
@RequiredArgsConstructor
public class RoutineResultController {

    private final RoutineResultService routineResultService;

    @ApiOperation(value = "루틴 결과 제출", notes = "루틴 결과(텍스트)와 선택적 파일을 업로드합니다. 멀티파트 요청입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "routineId", value = "루틴 ID", required = true, dataType = "long", paramType = "path"),
            @ApiImplicitParam(name = "file", value = "첨부 파일", required = false, dataType = "file", paramType = "form")
    })
    @PostMapping(value="/{routineId}/results")
    public ResponseEntity<RoutineResultCreateResponseDTO> submitRoutineResult(
            @PathVariable Long routineId,
            @ApiParam(value = "루틴 결과 생성 요청(JSON part)", required = true)
            @RequestPart("requestDTO") RoutineResultCreateRequestDTO requestDTO,
            @RequestPart(name = "file", required = false) MultipartFile file
    ) {
        RoutineResultCreateResponseDTO response = routineResultService.submitResult(routineId, requestDTO, file);
        return ResponseEntity.ok(response);
    }
}
