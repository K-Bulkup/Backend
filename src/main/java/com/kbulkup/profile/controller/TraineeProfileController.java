package com.kbulkup.profile.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.profile.dto.response.TraineeProfileDetailResponseDTO;
import com.kbulkup.profile.service.TraineeProfileService;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainee/profiles")
public class TraineeProfileController {

    private final TraineeProfileService traineeProfileService;

    @GetMapping("/me")
    public CustomResponse<TraineeProfileDetailResponseDTO> getTrainerProfile(@AuthenticationPrincipal(expression = "user") User user) {
        return CustomResponse.success(ResponseCode.SUCCESS, traineeProfileService.getTraineeProfile(user.getUserId()));
    }
}
