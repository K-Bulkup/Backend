package com.kbulkup.counseling.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.counseling.dto.request.CounselingCreateRequestDTO;
import com.kbulkup.counseling.dto.response.CounselingCreateResponseDTO;
import com.kbulkup.counseling.dto.response.CounselingDetailResponseDTO;
import com.kbulkup.counseling.dto.response.CounselingListResponseDTO;
import com.kbulkup.counseling.service.CounselingService;
import com.kbulkup.user.domain.User;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "Counseling", description = "상담방 조회/생성/상세 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common/counselings")
public class CounselingController {

    private final CounselingService counselingService;

    @ApiOperation(value = "상담방 목록 조회", notes = "내 상담방 목록을 최신순으로 조회합니다.")
    @GetMapping
    public CustomResponse<List<CounselingListResponseDTO>> readCounselings(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {
        return CustomResponse.success(ResponseCode.SUCCESS, counselingService.getCounselings(user.getUserId()));
    }

    @ApiOperation(value = "상담방 생성/가져오기", notes = "수강생/트레이닝 ID로 상담방을 생성하거나 기존 방을 반환합니다.")
    @PostMapping("")
    public CustomResponse<CounselingCreateResponseDTO> createCounselings(
            @ApiParam(value = "상담방 생성 요청", required = true)
            @RequestBody CounselingCreateRequestDTO dto) {
        return CustomResponse.success(
                ResponseCode.SUCCESS,
                counselingService.createOrGetCounselingsRoom(dto.getTraineeId(), dto.getTrainingId())
        );
    }

    @ApiOperation(value = "상담방 상세 조회", notes = "roomId로 상담방 상세 정보를 조회합니다.")
    @ApiImplicitParam(name = "roomId", value = "상담방 ID", required = true, dataType = "string", paramType = "path")
    @GetMapping("/detail/{roomId}")
    public CustomResponse<CounselingDetailResponseDTO> getCounselingDetail(
            @PathVariable String roomId,
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {
        return CustomResponse.success(ResponseCode.SUCCESS, counselingService.getCounselingDetail(roomId, user.getUserId()));
    }
}
