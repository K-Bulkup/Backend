package com.kbulkup.common.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AiChatScheduler {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String AI_CHAT_COUNT_PREFIX = "ai_chat_count:";

    // 매일 00시에 ai_chat_count: 로 시작하는 모든 키 삭제 (하루 채팅 횟수 제한 초기화)
    @Scheduled(cron = "0 0 0 * * *")
    public void resetAiChatCount() {
        redisTemplate.keys(AI_CHAT_COUNT_PREFIX + "*").forEach(redisTemplate::delete);
    }
}
