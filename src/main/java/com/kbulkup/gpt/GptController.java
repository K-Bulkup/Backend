package com.kbulkup.gpt;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.gpt.dto.request.ConsultRequestDTO;
import com.kbulkup.gpt.dto.response.GPTResponseDTO;
import com.kbulkup.gpt.service.GPTService;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gpt" )
@RequiredArgsConstructor
public class GptController {

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

    @PostMapping("/consulting")
    public CustomResponse<GPTResponseDTO> requestConsultAsset(@RequestBody ConsultRequestDTO consultRequestDTO, @AuthenticationPrincipal(expression = "user") User user) {
        GPTResponseDTO dto = gptService.requestCounseling(String.valueOf(user.getUserId()), consultRequestDTO.getQuestion(), consultRequestDTO.isAsset());
        return CustomResponse.success(ResponseCode.SUCCESS, dto);
    }
}
