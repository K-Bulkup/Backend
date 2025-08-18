package com.kbulkup.profile.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.profile.dto.request.TrainerProfileCareerUpdateRequestDTO;
import com.kbulkup.profile.dto.request.TrainerProfileImageUpdateRequestDTO;
import com.kbulkup.profile.dto.response.TrainerProfileDetailResponseDTO;
import com.kbulkup.profile.dto.response.TrainerProfileImgUrlResponseDTO;
import com.kbulkup.profile.service.TrainerProfileService;
import com.kbulkup.user.domain.User;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@Api(tags = "Trainer Profile", description = "트레이너 프로필/이미지/상세 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer/profiles")
public class TrainerProfileController {

    private final TrainerProfileService trainerProfileService;

    @ApiOperation(value = "내 프로필 조회(트레이너)", notes = "로그인한 트레이너의 프로필을 조회합니다.")
    @GetMapping("/me")
    public CustomResponse<TrainerProfileDetailResponseDTO> getTrainerProfile(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {
        TrainerProfileDetailResponseDTO dto = trainerProfileService.getTrainerProfile(user.getUserId());
        return CustomResponse.success(ResponseCode.SUCCESS, dto);
    }

    @ApiOperation(value = "트레이너 상세 조회(수강생용)", notes = "트레이너 프로필 및 운영중인 트레이닝 정보를 조회합니다.")
    @ApiImplicitParam(name = "trainerId", value = "트레이너 ID", required = true, dataType = "long", paramType = "path")
    @GetMapping("/{trainerId}")
    public CustomResponse<Map<String, Object>> getTrainerDetailForTrainee(@PathVariable Long trainerId) {
        return CustomResponse.success(ResponseCode.SUCCESS, trainerProfileService.getTrainerDetailForTrainee(trainerId));
    }

    @ApiOperation(value = "프로필 소개/경력 수정", notes = "트레이너 프로필의 소개/경력 정보를 수정합니다.")
    @PutMapping("/career")
    public CustomResponse<Void> putTrainerProfile(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user,
            @ApiParam(value = "프로필 경력 수정 요청 바디", required = true)
            @RequestBody TrainerProfileCareerUpdateRequestDTO dto) {
        return trainerProfileService.updateTrainerProfileCareer(user.getUserId(), dto);
    }

    @ApiOperation(value = "프로필 이미지 수정", notes = "멀티파트로 프로필 이미지를 업로드하여 수정합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "profileImage", value = "프로필 이미지 파일", required = true, dataType = "file", paramType = "form")
    })
    @PutMapping("/profile-image")
    public CustomResponse<TrainerProfileImgUrlResponseDTO> updateTrainerProfileImage(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user,
            @ModelAttribute TrainerProfileImageUpdateRequestDTO dto) {
        return CustomResponse.success(ResponseCode.SUCCESS,
                trainerProfileService.updateTrainerProfileImage(user.getUserId(), dto));
    }
}
