package com.kbulkup.chat.controller;

import com.kbulkup.chat.domain.MongoAiChatMessage;
import com.kbulkup.chat.domain.MongoChatMessage;
import com.kbulkup.chat.dto.ReadMessageDTO;
import com.kbulkup.chat.service.AiChatService;
import com.kbulkup.chat.service.ChatService;
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common/chats")
public class ChatRestController {

    private final ChatService chatService;
    private final AiChatService aiChatService;

    @GetMapping("/{roomId}/{userId}")
    public CustomResponse<List<MongoChatMessage>> getChatHistory(@PathVariable String roomId, @PathVariable String userId) {
        return CustomResponse.success(ResponseCode.SUCCESS, chatService.getMessagesByRoomId(roomId, userId));
    }

    @PostMapping("/read")
    public CustomResponse<Void> readMessages(@RequestBody ReadMessageDTO dto) {
        chatService.MarkMessagesAsRead(dto.getRoomId(), dto.getUserId());
        return CustomResponse.success(ResponseCode.SUCCESS);
    }

    @GetMapping("/ai/{userId}")
    public CustomResponse<List<MongoAiChatMessage>> getAiChatHistory(@PathVariable String userId) {
        return CustomResponse.success(ResponseCode.SUCCESS, aiChatService.getAiMessagesByUserId(userId));
    }
}
