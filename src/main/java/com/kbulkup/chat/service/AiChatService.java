package com.kbulkup.chat.service;

import com.kbulkup.chat.domain.MongoAiChatMessage;

import java.util.List;

public interface AiChatService {

    void saveAiChatMessage(String userId, String message, String role);

    List<MongoAiChatMessage> getAiMessagesByUserId(String userId);
}
