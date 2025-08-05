package com.kbulkup.chat.service;

import com.kbulkup.chat.domain.MongoAiChatMessage;
import com.kbulkup.chat.dto.AiChatHistoryResponseDTO;
import com.kbulkup.chat.repository.AiChatMongoRepository;
import com.kbulkup.common.exception.ChatException;
import com.kbulkup.common.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiChatServiceImpl implements AiChatService {

    private final AiChatMongoRepository aiChatMongoRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String AI_CHAT_COUNT_PREFIX = "ai_chat_count:";

    @Override
    @Transactional
    public int saveAiChatMessage(String userId, String message, String role) {

        String redisKey = AI_CHAT_COUNT_PREFIX + userId;
        String countStr = (String) redisTemplate.opsForValue().get(redisKey);
        int chatCount = (countStr == null) ? 0 : Integer.parseInt(countStr);

        if (chatCount <= 5) {
            MongoAiChatMessage document = MongoAiChatMessage.create(userId, message, role);
            aiChatMongoRepository.save(document);

            if (role.equals("user")) {
                redisTemplate.opsForValue().increment(redisKey, 1);
            }
        } else {
            throw new ChatException(ResponseCode.AI_CHAT_LIMIT_EXCEEDED);
        }

        return 5 - chatCount;
    }

    @Override
    public AiChatHistoryResponseDTO getAiMessagesByUserId(String userId) {

        String redisKey = AI_CHAT_COUNT_PREFIX + userId;
        String countStr = (String) redisTemplate.opsForValue().get(redisKey);
        int chatCount = (countStr == null) ? 0 : Integer.parseInt(countStr);

        if (countStr == null) {
            redisTemplate.opsForValue().set(redisKey, String.valueOf(chatCount));
        }

        List<MongoAiChatMessage> aiChatHistory = aiChatMongoRepository.findByUserId(userId);
        return AiChatHistoryResponseDTO.create(aiChatHistory, 5 - chatCount);
    }
}
