package com.kbulkup.gpt.service;

import com.kbulkup.chat.service.AiChatService;
import com.kbulkup.common.util.PromptBuilder;
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
@PropertySource("classpath:/config/application-dev.properties")
public class GPTServiceImpl implements GPTService {

    @Value("${openai.model}")
    private String apiModel;

    @Value("${openai.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    //테스트 용도
    private final AiChatService aiChatService;

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

    @Override
    public GPTResponseDTO requestCounseling(String userId, String question) {
        aiChatService.saveAiChatMessage(userId, question, "user");

        String prompt = PromptBuilder.buildAssetConsultingPrompt(question);
        GPTRequestDTO gptRequestDTO = GPTRequestDTO.createOnlyText(apiModel, "user", prompt, 300);

        aiChatService.saveAiChatMessage(userId, gptRequestDTO.getMessages().get(0).getContent().toString(), "assistant");

        return restTemplate.postForObject(apiUrl, gptRequestDTO, GPTResponseDTO.class);
    }
}
