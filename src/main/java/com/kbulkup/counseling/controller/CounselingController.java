package com.kbulkup.counseling.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.counseling.dto.response.TrainerCounselingListResponseDTO;
import com.kbulkup.counseling.service.CounselingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer/counselings")
public class CounselingController {

    private final CounselingService counselingService;

    @GetMapping("/{trainerId}")
    public CustomResponse<List<TrainerCounselingListResponseDTO>> readCounselings(@PathVariable Long trainerId) {
        //trainerId -> Authentication authentication 으로 변경 예정
        return CustomResponse.success(ResponseCode.SUCCESS, counselingService.getCounselingsByTrainer(trainerId));
    }
}
