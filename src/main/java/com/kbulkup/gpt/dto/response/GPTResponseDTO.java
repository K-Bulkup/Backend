package com.kbulkup.gpt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GPTResponseDTO {
    private String id;
    private List<ChoiceDTO> choices;
}
