package com.kbulkup.training.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.training.dto.response.TrainerDetailResponseDTO;
import com.kbulkup.training.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainee/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    @GetMapping("/{trainerId}")
    public CustomResponse<TrainerDetailResponseDTO> getTrainerDetail(@PathVariable Long trainerId) {

        TrainerDetailResponseDTO response = new TrainerDetailResponseDTO(
                trainerService.getTrainerProfile(trainerId),
                trainerService.getTrainerTrainings(trainerId)
        );

        return CustomResponse.success(ResponseCode.SUCCESS, response);
    }
}
