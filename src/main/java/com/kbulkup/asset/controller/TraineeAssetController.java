package com.kbulkup.asset.controller;

import com.kbulkup.asset.dto.request.TokenRequestDTO;
import com.kbulkup.asset.dto.response.TraineeAssetDetailResponseDTO;
import com.kbulkup.asset.service.TraineeAssetService;
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainee/assets")
public class TraineeAssetController {

    private final TraineeAssetService traineeAssetService;

    @GetMapping
    public CustomResponse<TraineeAssetDetailResponseDTO> getTraineeAsset(@AuthenticationPrincipal(expression = "user") User user) {
        TraineeAssetDetailResponseDTO dto = traineeAssetService.getTraineeAsset(user.getUserId());

        if (dto.getTransactions().isEmpty() && dto.getSnapshots().isEmpty() && dto.getComposition() == null) {
            return CustomResponse.success(ResponseCode.SUCCESS);
        }
        return CustomResponse.success(ResponseCode.SUCCESS, dto);
    }

    @PostMapping("/account")
    public CustomResponse<Void> postTraineeAccount(@RequestBody TokenRequestDTO dto, @AuthenticationPrincipal(expression = "user") User user) {
        traineeAssetService.createUserPortfolio(dto.getBank(), user.getUserId());

        return CustomResponse.success(ResponseCode.SUCCESS);
    }
}

