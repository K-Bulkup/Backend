package com.kbulkup.chat.repository;

import com.kbulkup.chat.domain.MongoAiChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AiChatMongoRepository {

    private final MongoTemplate mongoTemplate;

    public void save (MongoAiChatMessage mongoAiChatMessage) {
        mongoTemplate.save(mongoAiChatMessage);
    }

    public List<MongoAiChatMessage> findByUserId (String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        query.with(Sort.by(Sort.Direction.ASC, "sendAt"));

        return mongoTemplate.find(query, MongoAiChatMessage.class, "ai_chat_messages");
    }
}
