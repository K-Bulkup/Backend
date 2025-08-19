package com.kbulkup.gpt;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.gpt.dto.request.ConsultRequestDTO;
import com.kbulkup.gpt.dto.response.GPTResponseDTO;
import com.kbulkup.gpt.service.GPTService;
import com.kbulkup.user.domain.User;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "GPT", description = "GPT 텍스트/이미지 분석 및 상담 API")
@RestController
@RequestMapping("/api/gpt" )
@RequiredArgsConstructor
public class GptController {

    private final GPTService gptService;

    @ApiOperation(value = "텍스트 분석", notes = "미션과 사용자 답변으로 텍스트 분석을 요청합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mission", value = "미션 설명", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userAnswer", value = "사용자 답변", required = true, dataType = "string", paramType = "query")
    })
    @PostMapping("/text")
    public CustomResponse<GPTResponseDTO> requestTextWithImage(
            @RequestParam String mission,
            @RequestParam String userAnswer) {
        GPTResponseDTO dto = gptService.requestOnlyText(mission, userAnswer);
        return CustomResponse.success(ResponseCode.SUCCESS, dto);
    }

    @ApiOperation(value = "이미지 분석", notes = "미션과 이미지 URL을 전달하여 이미지 기반 분석을 요청합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mission", value = "미션 설명", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "imageUrl", value = "이미지 URL", required = true, dataType = "string", paramType = "query")
    })
    @PostMapping("/image")
    public CustomResponse<GPTResponseDTO> requestImageAnalysis(
            @RequestParam String mission,
            @RequestParam String imageUrl) {
        GPTResponseDTO dto = gptService.requestImageAnalysis(mission, imageUrl);
        return CustomResponse.success(ResponseCode.SUCCESS, dto);
    }

    @ApiOperation(value = "자산/일반 상담", notes = "사용자 질문과 자산 여부 플래그로 상담 답변을 요청합니다.")
    @PostMapping("/consulting")
    public CustomResponse<GPTResponseDTO> requestConsultAsset(
            @ApiParam(value = "상담 요청 바디", required = true)
            @RequestBody ConsultRequestDTO consultRequestDTO,
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {
        GPTResponseDTO dto = gptService.requestCounseling(
                String.valueOf(user.getUserId()),
                consultRequestDTO.getQuestion(),
                consultRequestDTO.isAsset()
        );
        return CustomResponse.success(ResponseCode.SUCCESS, dto);
    }
}
