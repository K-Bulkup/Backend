package com.kbulkup.common.controller.training;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.training.dto.request.TrainingSearchListRequestDTO;
import com.kbulkup.training.dto.response.TrainingSearchListResponseDTO;
import com.kbulkup.training.service.TrainingSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common/trainings")
public class TrainingSearchController {

    private final TrainingSearchService trainingSearchService;

    @GetMapping("/search")
    public CustomResponse<List<TrainingSearchListResponseDTO>> searchTrainings(
            @ModelAttribute TrainingSearchListRequestDTO dto
    ) {
        return CustomResponse.success(ResponseCode.SUCCESS, trainingSearchService.searchTrainings(dto));
    }
}
