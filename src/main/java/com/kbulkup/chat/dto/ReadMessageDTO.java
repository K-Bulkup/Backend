package com.kbulkup.chat.dto;

import lombok.Getter;

// Swagger
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "읽음 처리 요청")
@Getter
public class ReadMessageDTO {

    @ApiModelProperty(value = "채팅방 ID", required = true, example = "ROOM-abc123")
    private String roomId;

    @ApiModelProperty(value = "사용자 ID", required = true, example = "1001")
    private String userId;
}
