package com.kbulkup.chat.controller;

import com.kbulkup.chat.domain.MongoChatMessage;
import com.kbulkup.chat.service.ChatService;
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common/chats")
public class ChatRestController {

    private final ChatService chatService;

    @GetMapping("/{roomId}")
    public CustomResponse<List<MongoChatMessage>> getChatHistory(@PathVariable String roomId) {
        return CustomResponse.success(ResponseCode.SUCCESS, chatService.getMessagesByRoomId(roomId));
    }
}
