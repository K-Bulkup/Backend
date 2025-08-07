package com.kbulkup.qna.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerTrainingQnARequestDTO {
    private Long qnaId;
    private String answer;
}
