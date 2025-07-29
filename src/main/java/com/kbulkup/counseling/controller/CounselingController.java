package com.kbulkup.counseling.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.common.security.CustomUserDetails;
import com.kbulkup.counseling.dto.request.CounselingCreateRequestDTO;
import com.kbulkup.counseling.dto.response.CounselingCreateResponseDTO;
import com.kbulkup.counseling.dto.response.CounselingDetailResponseDTO;
import com.kbulkup.counseling.dto.response.TrainerCounselingListResponseDTO;
import com.kbulkup.counseling.service.CounselingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common/counselings")
public class CounselingController {

    private final CounselingService counselingService;

    @GetMapping("/{userId}")
    public CustomResponse<List<TrainerCounselingListResponseDTO>> readCounselings(@PathVariable Long userId) {
        //trainerId -> Authentication authentication 으로 변경 예정
        return CustomResponse.success(ResponseCode.SUCCESS, counselingService.getCounselings(userId));
    }

    @PostMapping("")
    public CustomResponse<CounselingCreateResponseDTO> createCounselings(@RequestBody CounselingCreateRequestDTO dto) {
        return CustomResponse.success(ResponseCode.SUCCESS, counselingService.createOrGetCounselingsRoom(dto.getTraineeId(), dto.getTrainingId()));
    }

    @GetMapping("/detail/{roomId}/{userId}")
    public CustomResponse<CounselingDetailResponseDTO> getCounselingDetail(@PathVariable String roomId, @PathVariable Long userId) {
        return CustomResponse.success(ResponseCode.SUCCESS, counselingService.getCounselingDetail(roomId, userId));
    }
}
