// --- RoutineResultCreateRequestDTO.java ---
package com.kbulkup.routine.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoutineResultCreateRequestDTO {
    private Long enrollmentId;
    private String answerText;
    private String evidenceUrl;

    @Builder
    public RoutineResultCreateRequestDTO(Long enrollmentId, String answerText, String evidenceUrl) {
        this.enrollmentId = enrollmentId;
        this.answerText = answerText;
        this.evidenceUrl = evidenceUrl;
    }
}
