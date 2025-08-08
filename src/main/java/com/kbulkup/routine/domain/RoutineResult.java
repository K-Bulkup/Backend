// --- RoutineResult.java ---
package com.kbulkup.routine.domain;

import com.kbulkup.routine.dto.response.RoutineResultCreateResponseDTO.PassFailResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RoutineResult {

    private Long routineResultId;
    private Long enrollmentId;
    private Long routineId;
    private Boolean status;
    private Integer awaredScore;
    private PassFailResult passFailResult;
    private String answerText;
    private String evidenceUrl;
    private LocalDateTime submittedAt;

    @Builder
    public RoutineResult(Long routineResultId, Long enrollmentId, Long routineId, Boolean status, Integer awaredScore,
                         PassFailResult passFailResult, String answerText, String evidenceUrl, LocalDateTime submittedAt) {
        this.routineResultId = routineResultId;
        this.enrollmentId = enrollmentId;
        this.routineId = routineId;
        this.status = status;
        this.awaredScore = awaredScore;
        this.passFailResult = passFailResult;
        this.answerText = answerText;
        this.evidenceUrl = evidenceUrl;
        this.submittedAt = submittedAt;
    }
}
