package com.kbulkup.common.controller.training;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.training.dto.request.TrainingSearchListRequestDTO;
import com.kbulkup.training.dto.response.TrainingSearchListResponseDTO;
import com.kbulkup.training.service.TrainingSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/common/trainings")
@RequiredArgsConstructor
public class TrainingSearchController {

    private final TrainingSearchService trainingSearchService;

    @GetMapping("/search")
    public CustomResponse<List<TrainingSearchListResponseDTO>> searchTrainings(
            @ModelAttribute TrainingSearchListRequestDTO request
    ) {
        // keyword 비어있을 경우 빈 리스트 반환
        if (request.getKeyword() == null || request.getKeyword().isBlank()) {
            return CustomResponse.success(ResponseCode.SUCCESS, List.of());
        }

        return CustomResponse.success(ResponseCode.SUCCESS, trainingSearchService.searchTrainings(request));
    }
}
