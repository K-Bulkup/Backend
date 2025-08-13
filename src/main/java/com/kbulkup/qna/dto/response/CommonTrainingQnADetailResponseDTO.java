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
public class CommonTrainingQnADetailResponseDTO {
    private Long qnaId;
    private String userName;
    private String questionTitle;
    private String question;
    private String answer;
    private LocalDateTime createAt;
    private LocalDateTime answeredAt;

    public static CommonTrainingQnADetailResponseDTO create(Long qnaId, String userName, String question, String answer, LocalDateTime createAt, LocalDateTime answeredAt) {
        return CommonTrainingQnADetailResponseDTO.builder()
                .qnaId(qnaId)
                .userName(userName)
                .question(question)
                .answer(answer)
                .createAt(createAt)
                .answeredAt(answeredAt).build();
    }
}
