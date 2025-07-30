package com.kbulkup.chat.controller;

import com.kbulkup.chat.dto.ChatMessageDTO;
import com.kbulkup.chat.dto.ChatSummaryDTO;
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
    public void sendMessage(ChatMessageDTO chatMessageDTO) {
        chatMessageDTO.setSendAt(LocalDateTime.now());
        ChatSummaryDTO chatSummary = chatService.saveChatMessage(chatMessageDTO);

        // 만료된 방이면 아무것도 하지 않음
        if (chatSummary == null) return;

        //채팅방에 메시지 실시간 전송
        messagingTemplate.convertAndSend("/topic/room/" + chatMessageDTO.getRoomId(), chatMessageDTO);

        //요약 정보 채팅방 리스트에 실시간 전송
        messagingTemplate.convertAndSend("/queue/user/" + chatSummary.getReceiverId(), chatSummary);
    }
}
