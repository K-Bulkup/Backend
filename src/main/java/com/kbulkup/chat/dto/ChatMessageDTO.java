package com.kbulkup.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// Swagger
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "채팅 메시지 (WebSocket)")
@Getter
public class ChatMessageDTO {

    @ApiModelProperty(value = "채팅방 ID", required = true)
    private String roomId;

    @ApiModelProperty(value = "보낸 사용자 ID", required = true)
    private String senderId;

    @ApiModelProperty(value = "메시지 내용(개인정보 포함 가능)", required = true)
    private String message;

    @Setter
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(value = "발송 시각(서버에서 세팅)")
    private LocalDateTime sendAt;
}
