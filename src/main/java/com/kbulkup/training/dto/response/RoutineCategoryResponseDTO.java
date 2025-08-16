package com.kbulkup.training.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoutineCategoryResponseDTO {
    private String category;       // '스트레칭', '근력', '유산소'
    private String routineTitle;
    private String quizType;
}
