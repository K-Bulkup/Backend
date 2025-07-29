package com.kbulkup.asset.controller;

import com.kbulkup.asset.dto.request.TokenRequestDTO;
import com.kbulkup.asset.dto.response.TraineeAssetDetailResponseDTO;
import com.kbulkup.asset.service.TraineeAssetService;
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainee/assets")
public class TraineeAssetController {

    private final TraineeAssetService traineeAssetService;

    @GetMapping("/{traineeId}")
    public CustomResponse<TraineeAssetDetailResponseDTO> getTraineeAsset(@PathVariable Long traineeId) {
        TraineeAssetDetailResponseDTO dto = traineeAssetService.getTraineeAsset(traineeId);

        if (dto.getTransactions().isEmpty() && dto.getSnapshots().isEmpty() && dto.getComposition() == null) {
            return CustomResponse.success(ResponseCode.SUCCESS);
        }
        return CustomResponse.success(ResponseCode.SUCCESS, dto);
    }

    @PostMapping("/account/{traineeId}")
    public CustomResponse<Void> postTraineeAccount(@RequestBody TokenRequestDTO dto, @PathVariable Long traineeId) {
        traineeAssetService.createUserPortfolio(dto.getBank(), traineeId);

        return CustomResponse.success(ResponseCode.SUCCESS);
    }
}
