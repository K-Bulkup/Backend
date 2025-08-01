package com.kbulkup.gpt;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.gpt.dto.response.GPTResponseDTO;
import com.kbulkup.gpt.service.GPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gpt" )
@RequiredArgsConstructor
public class GptController { //테스트용 컨트롤러입니다.

    private final GPTService gptService;

    @PostMapping("/text")
    public CustomResponse<GPTResponseDTO> requestTextWithImage(@RequestParam String mission, @RequestParam String userAnswer) {
        GPTResponseDTO dto = gptService.requestOnlyText(mission, userAnswer);
        return CustomResponse.success(ResponseCode.SUCCESS, dto);
    }

    @PostMapping("/image")
    public CustomResponse<GPTResponseDTO> requestImageAnalysis(@RequestParam String mission, @RequestParam String imageUrl) {
        GPTResponseDTO dto = gptService.requestImageAnalysis(mission, imageUrl);
        return CustomResponse.success(ResponseCode.SUCCESS, dto);
    }

    @PostMapping("/consulting/{userId}")
    public CustomResponse<GPTResponseDTO> requestConsultAsset(@RequestParam String question, @PathVariable String userId) {
        GPTResponseDTO dto = gptService.requestCounseling(userId, question);
        return CustomResponse.success(ResponseCode.SUCCESS, dto);
    }
}
