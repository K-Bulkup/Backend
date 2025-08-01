package com.kbulkup.gpt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromptTokensDetailsDTO {
    private int cached_tokens;
    private int audio_tokens;
}

