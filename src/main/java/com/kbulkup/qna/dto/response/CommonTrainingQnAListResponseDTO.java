package com.kbulkup.qna.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonTrainingQnAListResponseDTO {
    private Long userId;
    private String question;
    private String answer;
    private LocalDateTime createAt;
    private LocalDateTime answeredAt;

    public static CommonTrainingQnAListResponseDTO create(Long userId, String question, String answer, LocalDateTime createAt, LocalDateTime answeredAt) {
        return CommonTrainingQnAListResponseDTO.builder()
                .userId(userId)
                .question(question)
                .answer(answer)
                .createAt(createAt)
                .answeredAt(answeredAt).build();
    }
}
