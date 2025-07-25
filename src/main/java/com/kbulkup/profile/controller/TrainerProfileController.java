package com.kbulkup.profile.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;
import com.kbulkup.profile.service.TrainerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer/profiles")
public class TrainerProfileController {

    private final TrainerProfileService trainerProfileService;

    @GetMapping("/me/{trainerId}")
    public CustomResponse<TrainerProfileDetailResponseDTO> getTrainerProfile(@PathVariable Long trainerId) {
        TrainerProfileDetailResponseDTO dto = trainerProfileService.getTrainerProfile(trainerId);
        return CustomResponse.success(ResponseCode.SUCCESS, dto);
    }
}
