package com.kbulkup.chat.controller;

import com.kbulkup.chat.domain.ChatMessage;
import com.kbulkup.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/send-message")
    public void sendMessage(ChatMessage chatMessage) {
        chatMessage.setSendAt(LocalDateTime.now());
        chatService.saveChatMessage(chatMessage);
        messagingTemplate.convertAndSend("/topic/room/" + chatMessage.getRoomId(), chatMessage);
    }
}
