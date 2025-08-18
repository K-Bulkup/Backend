package com.kbulkup.chat.controller;

import com.kbulkup.chat.dto.ChatMessageDTO;
import com.kbulkup.chat.dto.ChatSummaryDTO;
import com.kbulkup.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;

@ApiIgnore // WebSocket 엔드포인트는 Swagger 문서에서 제외
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/send-message")
    public void sendMessage(ChatMessageDTO chatMessageDTO) {
        chatMessageDTO.setSendAt(LocalDateTime.now());
        var chatSummary = chatService.saveChatMessage(chatMessageDTO);
        if (chatSummary == null) return;
        messagingTemplate.convertAndSend("/topic/room/" + chatMessageDTO.getRoomId(), chatMessageDTO);
        messagingTemplate.convertAndSend("/queue/user/" + chatSummary.getReceiverId(), chatSummary);
    }
}
