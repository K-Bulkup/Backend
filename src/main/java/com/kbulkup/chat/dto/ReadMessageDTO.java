package com.kbulkup.chat.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(description = "읽음 처리 요청")
@Getter
public class ReadMessageDTO {

    @ApiModelProperty(value = "채팅방 ID", required = true)
    private String roomId;

    @ApiModelProperty(value = "사용자 ID", required = true)
    private String userId;
}
