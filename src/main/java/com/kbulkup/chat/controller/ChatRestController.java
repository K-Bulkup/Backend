package com.kbulkup.chat.controller;

import com.kbulkup.chat.domain.MongoChatMessage;
import com.kbulkup.chat.dto.ReadMessageDTO;
import com.kbulkup.chat.service.ChatService;
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common/chats")
public class ChatRestController {

    private final ChatService chatService;

    @GetMapping("/{roomId}")
    public CustomResponse<List<MongoChatMessage>> getChatHistory(@PathVariable String roomId, @AuthenticationPrincipal(expression = "user") User user) {
        return CustomResponse.success(ResponseCode.SUCCESS, chatService.getMessagesByRoomId(roomId, String.valueOf(user.getUserId())));
    }

    @PostMapping("/read")
    public CustomResponse<Void> readMessages(@RequestBody ReadMessageDTO dto) {
        chatService.MarkMessagesAsRead(dto.getRoomId(), dto.getUserId());
        return CustomResponse.success(ResponseCode.SUCCESS);
    }
}
