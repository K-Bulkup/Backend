package com.kbulkup.gpt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsageDTO {
    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;
    private PromptTokensDetailsDTO prompt_tokens_details;
    private CompletionTokensDetailsDTO completion_tokens_details;
}
