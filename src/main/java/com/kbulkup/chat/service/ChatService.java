package com.kbulkup.chat.service;

import com.kbulkup.chat.domain.ChatMessage;
import com.kbulkup.chat.domain.MongoChatMessage;

import java.util.List;

public interface ChatService {

    void saveChatMessage(ChatMessage chatMessage);

    List<MongoChatMessage> getMessagesByRoomId(String roomId, String userId);
}
