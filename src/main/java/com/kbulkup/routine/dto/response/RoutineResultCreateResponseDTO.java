// --- RoutineResultCreateResponseDTO.java ---
package com.kbulkup.routine.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoutineResultCreateResponseDTO {
    private PassFailResult passFailResult;

    public enum PassFailResult {
        PASS, FAIL, PENDING
    }

    public static RoutineResultCreateResponseDTO from(PassFailResult result) {
        return new RoutineResultCreateResponseDTO(result);
    }
}
