package com.kbulkup.counseling.dto.response;

import com.kbulkup.counseling.domain.Counseling;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CounselingCreateResponseDTO {

    private String roomId;
    private boolean isNew;

    public static CounselingCreateResponseDTO toDTO(String roomId, boolean isNew) {
        return CounselingCreateResponseDTO.builder()
                .roomId(roomId)
                .isNew(isNew)
                .build();
    }
}
