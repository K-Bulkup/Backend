package com.kbulkup.chat.service;

import com.kbulkup.chat.dto.AiChatHistoryResponseDTO;

public interface AiChatService {

    int saveAiChatMessage(String userId, String message, String role);

    AiChatHistoryResponseDTO getAiMessagesByUserId(String userId);
}
