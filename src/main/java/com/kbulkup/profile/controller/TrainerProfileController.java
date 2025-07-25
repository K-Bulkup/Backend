package com.kbulkup.profile.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;
import com.kbulkup.profile.service.TrainerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer")
public class TrainerProfileController {

    private final TrainerProfileService trainerProfileService;

    @GetMapping("/profiles/me/{trainerId}")
    public CustomResponse<TrainerProfileDetailResponseDTO> getTrainerProfile(@PathVariable Long trainerId) {
        TrainerProfileDetailResponseDTO dto = trainerProfileService.getTrainerProfile(trainerId);
        return CustomResponse.success(ResponseCode.SUCCESS, dto);
    }
}
