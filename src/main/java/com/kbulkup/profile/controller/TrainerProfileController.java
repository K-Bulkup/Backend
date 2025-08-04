package com.kbulkup.profile.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.profile.dto.request.TrainerProfileCareerUpdateRequestDTO;
import com.kbulkup.profile.dto.request.TrainerProfileImageUpdateRequestDTO;
import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;
import com.kbulkup.profile.service.TrainerProfileService;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer/profiles")
public class TrainerProfileController {

    private final TrainerProfileService trainerProfileService;

    @GetMapping("/me")
    public CustomResponse<TrainerProfileDetailResponseDTO> getTrainerProfile(@AuthenticationPrincipal(expression = "user") User user) {
        TrainerProfileDetailResponseDTO dto = trainerProfileService.getTrainerProfile(user.getUserId());
        return CustomResponse.success(ResponseCode.SUCCESS, dto);
    }

    @PutMapping("/career")
    public CustomResponse<Void> putTrainerProfile(@AuthenticationPrincipal(expression = "user") User user,
                                                  @RequestBody TrainerProfileCareerUpdateRequestDTO dto){
        return trainerProfileService.updateTrainerProfileCareer(user.getUserId(),dto);
    }

    @PutMapping("/profile-image")
    public CustomResponse<Void> updateTrainerProfileImage(
            @AuthenticationPrincipal(expression = "user") User user,
            @ModelAttribute TrainerProfileImageUpdateRequestDTO dto) {
        trainerProfileService.updateTrainerProfileImage(user.getUserId(), dto);
        return CustomResponse.success(ResponseCode.SUCCESS);
    }
}
