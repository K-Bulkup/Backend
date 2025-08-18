package com.kbulkup.profile.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.profile.dto.response.TraineeProfileDetailResponseDTO;
import com.kbulkup.profile.service.TraineeProfileService;
import com.kbulkup.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Trainee Profile", description = "수강생 프로필 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainee/profiles")
public class TraineeProfileController {

    private final TraineeProfileService traineeProfileService;

    @ApiOperation(value = "내 프로필 조회", notes = "로그인한 수강생의 프로필을 조회합니다.")
    @GetMapping("/me")
    public CustomResponse<TraineeProfileDetailResponseDTO> getTrainerProfile(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {
        return CustomResponse.success(ResponseCode.SUCCESS, traineeProfileService.getTraineeProfile(user.getUserId()));
    }
}
