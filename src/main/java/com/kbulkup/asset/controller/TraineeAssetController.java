package com.kbulkup.asset.controller;

import com.kbulkup.asset.dto.request.TokenRequestDTO;
import com.kbulkup.asset.dto.response.TraineeAssetDetailResponseDTO;
import com.kbulkup.asset.service.TraineeAssetService;
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.user.domain.User;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Trainee Asset", description = "수강생 자산/포트폴리오 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainee/assets")
public class TraineeAssetController {

    private final TraineeAssetService traineeAssetService;

    @ApiOperation(value = "내 자산 조회", notes = "로그인한 수강생의 포트폴리오(거래내역, 스냅샷, 구성)를 조회합니다. 데이터가 비어있으면 data는 null로 반환됩니다.")
    @GetMapping
    public CustomResponse<TraineeAssetDetailResponseDTO> getTraineeAsset(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {
        TraineeAssetDetailResponseDTO dto = traineeAssetService.getTraineeAsset(user.getUserId());
        if (dto.getTransactions().isEmpty() && dto.getSnapshots().isEmpty() && dto.getComposition() == null) {
            return CustomResponse.success(ResponseCode.SUCCESS);
        }
        return CustomResponse.success(ResponseCode.SUCCESS, dto);
    }

    @ApiOperation(value = "자산 계정 연결 생성", notes = "선택한 은행으로 포트폴리오 연동을 생성합니다.")
    @PostMapping("/account")
    public CustomResponse<Void> postTraineeAccount(
            @ApiParam(value = "은행 식별 정보", required = true)
            @RequestBody TokenRequestDTO dto,
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {
        traineeAssetService.createUserPortfolio(dto.getBank(), dto.getAccountNumber(), user);
        return CustomResponse.success(ResponseCode.SUCCESS);
    }

    @ApiOperation(value = "자산 갱신 후 조회", notes = "외부 연동을 통해 포트폴리오를 갱신하고 최신 데이터를 반환합니다.")
    @PutMapping
    public CustomResponse<TraineeAssetDetailResponseDTO> updateAndGetTraineeAsset(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {
        traineeAssetService.updateUserPortfolio(user);
        return CustomResponse.success(ResponseCode.SUCCESS, traineeAssetService.getTraineeAsset(user.getUserId()));
    }

    @ApiOperation(value = "트레이너 공유용 수강생 자산 조회", notes = "상담/트레이닝 룸 ID로 수강생 자산 정보를 조회합니다.")
    @ApiImplicitParam(name = "roomId", value = "룸 ID", required = true, dataType = "string", paramType = "path")
    @GetMapping("/trainer-share/{roomId}")
    public CustomResponse<TraineeAssetDetailResponseDTO> getTraineeAssetToTrainer(@PathVariable String roomId) {
        return CustomResponse.success(ResponseCode.SUCCESS, traineeAssetService.findTraineeAssetDetailByRoomID(roomId));
    }
}
