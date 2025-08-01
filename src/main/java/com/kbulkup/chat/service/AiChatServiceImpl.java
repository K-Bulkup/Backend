package com.kbulkup.chat.service;

import com.kbulkup.chat.domain.MongoAiChatMessage;
import com.kbulkup.chat.domain.MongoChatMessage;
import com.kbulkup.chat.repository.AiChatMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiChatServiceImpl implements AiChatService {

    private final AiChatMongoRepository aiChatMongoRepository;

    @Override
    @Transactional
    public void saveAiChatMessage(String userId, String message, String role) {

        MongoAiChatMessage document = MongoAiChatMessage.create(userId, message, role);
        System.out.println();
        aiChatMongoRepository.save(document);
    }

    @Override
    public List<MongoAiChatMessage> getAiMessagesByUserId(String userId) {
        return aiChatMongoRepository.findByUserId(userId);
    }
}
