// --- AiJudgeClient.java ---
package com.kbulkup.routine.client;

import com.kbulkup.gpt.dto.response.GPTResponseDTO;
import com.kbulkup.gpt.service.GPTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AiJudgeClient {

    private final GPTService gptService;

    public boolean evaluate(String routineDescription, String userAnswer, String evidenceUrl) {
        GPTResponseDTO response;

        if (evidenceUrl != null && !evidenceUrl.isBlank()) {
            response = gptService.requestImageAnalysis(routineDescription, evidenceUrl);
        } else {
            response = gptService.requestOnlyText(routineDescription, userAnswer);
        }

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            log.warn("AI 판별 실패: 응답 없음 또는 비어 있음");
            return false;
        }

        String content = String.valueOf(response.getChoices().get(0).getMessage().getContent()).toUpperCase();
        log.info("GPT 응답 결과: {}", content);

        // return content.matches(".*(PASS|정답|맞|정확|TRUE).*");
        return content.toLowerCase().contains("true");
    }
}
