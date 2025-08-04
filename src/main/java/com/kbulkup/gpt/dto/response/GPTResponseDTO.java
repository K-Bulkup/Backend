package com.kbulkup.gpt.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GPTResponseDTO {
    private String id;

    @Setter
    private int remainingChats;
    private List<ChoiceDTO> choices;
}
