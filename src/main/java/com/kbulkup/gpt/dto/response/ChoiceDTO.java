package com.kbulkup.gpt.dto.response;

import com.kbulkup.gpt.dto.common.MessageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceDTO {
    private int index;
    private MessageDTO message;
}
