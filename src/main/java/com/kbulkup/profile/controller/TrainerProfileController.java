package com.kbulkup.profile.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.profile.dto.request.TrainerProfileCareerUpdateRequestDTO;
import com.kbulkup.profile.dto.request.TrainerProfileImageUpdateRequestDTO;
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

    @PutMapping("/career/{trainerId}")
    public CustomResponse<Void> putTrainerProfile(@PathVariable Long trainerId,
                                                  @RequestBody TrainerProfileCareerUpdateRequestDTO dto){
        return trainerProfileService.updateTrainerProfileCareer(trainerId,dto);
    }

    @PutMapping("/profile-image/{trainerId}")
    public CustomResponse<Void> updateTrainerProfileImage(
            @PathVariable Long trainerId,
            @RequestBody TrainerProfileImageUpdateRequestDTO dto) {
        return trainerProfileService.updateTrainerProfileImage(trainerId, dto);
    }

}
