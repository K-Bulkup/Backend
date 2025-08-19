package com.kbulkup.counseling.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "상담방 생성 응답")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CounselingCreateResponseDTO {

    @ApiModelProperty(value = "상담방 ID", example = "ROOM-abc123")
    private String roomId;

    @ApiModelProperty(value = "신규 생성 여부", example = "true")
    private boolean isNew;

    public static CounselingCreateResponseDTO toDTO(String roomId, boolean isNew) {
        return CounselingCreateResponseDTO.builder()
                .roomId(roomId)
                .isNew(isNew)
                .build();
    }
}
