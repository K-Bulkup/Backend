package com.kbulkup.profile.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.profile.dto.request.TrainerProfileCareerUpdateRequestDTO;
import com.kbulkup.profile.dto.request.TrainerProfileImageUpdateRequestDTO;
import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;
import com.kbulkup.profile.dto.response.TrainerProfileImgUrlResponseDTO;
import com.kbulkup.profile.service.TrainerProfileService;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer/profiles")
public class TrainerProfileController {

    private final TrainerProfileService trainerProfileService;

    @GetMapping("/me")
    public CustomResponse<TrainerProfileDetailResponseDTO> getTrainerProfile(
            @AuthenticationPrincipal(expression = "user") User user) {
        TrainerProfileDetailResponseDTO dto = trainerProfileService.getTrainerProfile(user.getUserId());
        return CustomResponse.success(ResponseCode.SUCCESS, dto);
    }

    // 트레이니가 보는 트레이너 상세 (프로필 + 운영중 트레이닝)
    @GetMapping("/{trainerId}")
    public CustomResponse<Map<String, Object>> getTrainerDetailForTrainee(
            @PathVariable Long trainerId) {
        return CustomResponse.success(ResponseCode.SUCCESS,
                trainerProfileService.getTrainerDetailForTrainee(trainerId));
    }

    @PutMapping("/career")
    public CustomResponse<Void> putTrainerProfile(
            @AuthenticationPrincipal(expression = "user") User user,
            @RequestBody TrainerProfileCareerUpdateRequestDTO dto) {
        return trainerProfileService.updateTrainerProfileCareer(user.getUserId(), dto);
    }

    @PutMapping("/profile-image")
    public CustomResponse<TrainerProfileImgUrlResponseDTO> updateTrainerProfileImage(
            @AuthenticationPrincipal(expression = "user") User user,
            @ModelAttribute TrainerProfileImageUpdateRequestDTO dto) {
        return CustomResponse.success(ResponseCode.SUCCESS,
                trainerProfileService.updateTrainerProfileImage(user.getUserId(), dto));
    }
}
