package com.kbulkup.gpt.service;

import com.kbulkup.gpt.dto.request.GPTRequestDTO;
import com.kbulkup.gpt.dto.response.GPTResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Primary
@RequiredArgsConstructor
@PropertySource("classpath:application-secret.properties")
public class GPTServiceImpl implements GPTService {
    @Value("${openai.model}")
    private String apiModel;

    @Value("${openai.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    @Override
    public GPTResponseDTO requestOnlyText(String mission, String userAnswer) {
        String prompt = PromptBuilder.buildTextPrompt(mission, userAnswer);
        GPTRequestDTO gptRequestDTO = GPTRequestDTO.createOnlyText(apiModel, "user", prompt, 300);
        return restTemplate.postForObject(apiUrl, gptRequestDTO, GPTResponseDTO.class);
    }

    @Override
    public GPTResponseDTO requestImageAnalysis(String mission, String imageUrl) {
        String prompt = PromptBuilder.buildImagePrompt(mission);
        GPTRequestDTO gptRequestDTO = GPTRequestDTO.createWithTextAndImage(apiModel, "user",  prompt, imageUrl, 500);
        return restTemplate.postForObject(apiUrl, gptRequestDTO, GPTResponseDTO.class);
    }

    private String buildTextPrompt(String mission, String userAnswer) {
        return """
        당신은 주관식 미션을 채점하는 전문가입니다.
        
        아래는 사용자의 미션과 그에 대한 답변입니다.
        
        [미션]
        %s
        
        [사용자 답변]
        %s
        
        미션의 핵심 요구사항을 충족했다면 [true], 아니라면 [false]로만 응답하십시오.
        이유나 해설 없이 오직 [true] 또는 [false]만 출력하십시오.
        """.formatted(mission, userAnswer);
    }

    private String buildImagePrompt(String mission) {
        return """
        당신은 이미지 채점 전문가입니다.

        아래 미션을 읽고, 첨부된 이미지를 분석하여 사용자가 미션을 수행했는지 평가하십시오.

        [미션]
        %s

        이미지를 분석한 후, 미션을 제대로 수행한 것이 명확하다면 [true], 그렇지 않다면 [false]로만 응답하십시오.
        이유나 해설은 생략하고 반드시 [true] 또는 [false]만 출력하십시오.
        """.formatted(mission);
    }
}
