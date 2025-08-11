// --- RoutineResultCreateRequestDTO.java ---
package com.kbulkup.routine.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoutineResultCreateRequestDTO {
    private Long enrollmentId;
    private String answerText;
}
