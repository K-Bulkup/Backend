package com.kbulkup.gpt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompletionTokensDetailsDTO {
    private int reasoning_tokens;
    private int audio_tokens;
    private int accepted_prediction_tokens;
    private int rejected_prediction_tokens;
}

