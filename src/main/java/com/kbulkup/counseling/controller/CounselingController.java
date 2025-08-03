package com.kbulkup.counseling.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.counseling.dto.request.CounselingCreateRequestDTO;
import com.kbulkup.counseling.dto.response.CounselingCreateResponseDTO;
import com.kbulkup.counseling.dto.response.CounselingDetailResponseDTO;
import com.kbulkup.counseling.dto.response.CounselingListResponseDTO;
import com.kbulkup.counseling.service.CounselingService;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common/counselings")
public class CounselingController {

    private final CounselingService counselingService;

    @GetMapping
    public CustomResponse<List<CounselingListResponseDTO>> readCounselings(@AuthenticationPrincipal(expression = "user") User user) {
        return CustomResponse.success(ResponseCode.SUCCESS, counselingService.getCounselings(user.getUserId()));
    }

    @PostMapping("")
    public CustomResponse<CounselingCreateResponseDTO> createCounselings(@RequestBody CounselingCreateRequestDTO dto) {
        return CustomResponse.success(ResponseCode.SUCCESS, counselingService.createOrGetCounselingsRoom(dto.getTraineeId(), dto.getTrainingId()));
    }

    @GetMapping("/detail/{roomId}")
    public CustomResponse<CounselingDetailResponseDTO> getCounselingDetail(@PathVariable String roomId, @AuthenticationPrincipal(expression = "user") User user) {
        return CustomResponse.success(ResponseCode.SUCCESS, counselingService.getCounselingDetail(roomId, user.getUserId()));
    }
}
