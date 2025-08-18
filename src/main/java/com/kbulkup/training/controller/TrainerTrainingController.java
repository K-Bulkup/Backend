package com.kbulkup.training.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.training.dto.response.TrainerDetailResponseDTO;
import com.kbulkup.training.service.TrainerService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Trainer", description = "트레이너 상세 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainee/trainers")
public class TrainerTrainingController {

    private final TrainerService trainerService;

    @ApiOperation(value = "트레이너 상세 조회(트레이니용)")
    @ApiImplicitParam(name = "trainerId", value = "트레이너 ID", required = true, dataType = "long", paramType = "path")
    @GetMapping("/{trainerId}")
    public CustomResponse<TrainerDetailResponseDTO> getTrainerDetail(@PathVariable Long trainerId) {
        return CustomResponse.success(ResponseCode.SUCCESS, trainerService.getTrainerDetail(trainerId));
    }
}
