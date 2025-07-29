package com.kbulkup.chat.repository;

import com.kbulkup.chat.domain.MongoChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatMongoRepository {

    private final MongoTemplate mongoTemplate;

    public void save(MongoChatMessage mongoChatMessage) {
        mongoTemplate.save(mongoChatMessage);
    }

    public void saveAll(List<MongoChatMessage> mongoChatMessages) {
        mongoChatMessages.forEach(this::save);
    }

    public List<MongoChatMessage> findByRoomId(String roomId) {
        Query query = new Query(Criteria.where("roomId").is(roomId));
        query.with(Sort.by(Sort.Direction.ASC, "sendAt"));

        return mongoTemplate.find(query, MongoChatMessage.class, "chat_messages");
    }

    public long countUnreadMessages(String roomId, String receiverId) {
        Query query = new Query(Criteria.where("roomId").is(roomId)
                .and("receiverId").is(receiverId)
                .and("isRead").is(false));

        return mongoTemplate.count(query, MongoChatMessage.class, "chat_messages");
    }
}
