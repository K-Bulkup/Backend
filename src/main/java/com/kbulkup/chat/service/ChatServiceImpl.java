package com.kbulkup.chat.service;

import com.kbulkup.chat.dto.ChatMessageDTO;
import com.kbulkup.chat.domain.MongoChatMessage;
import com.kbulkup.chat.dto.ChatSummaryDTO;
import com.kbulkup.chat.repository.ChatMongoRepository;
import com.kbulkup.counseling.domain.Counseling;
import com.kbulkup.counseling.mapper.CounselingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMongoRepository chatMongoRepository;
    private final CounselingMapper counselingMapper;

    @Override
    @Transactional
    public ChatSummaryDTO saveChatMessage(ChatMessageDTO chatMessageDTO) {

        Counseling counseling = counselingMapper.findByRoomId(chatMessageDTO.getRoomId());
        String receiverId = chatMessageDTO.getSenderId().equals(counseling.getUserId().toString())
                ? counseling.getTrainerId().toString() : counseling.getUserId().toString();

        //mongodb 채팅 내역 저장
        MongoChatMessage document = MongoChatMessage.create(chatMessageDTO, receiverId);
        chatMongoRepository.save(document);

        //mysql 마지막 채팅 시간, 마지막 채팅 업데이트
        counselingMapper.updateLatestMessage(chatMessageDTO.getRoomId(), chatMessageDTO.getMessage(), chatMessageDTO.getSendAt());

        //채팅방 요약 정보 전송 위함
        int unreadCount = (int) chatMongoRepository.countUnreadMessages(chatMessageDTO.getRoomId(), receiverId);
        return ChatSummaryDTO.create(chatMessageDTO, unreadCount, receiverId);
    }

    @Override
    @Transactional
    public List<MongoChatMessage> getMessagesByRoomId(String roomId, String userId) {
        List<MongoChatMessage> messages = chatMongoRepository.findByRoomId(roomId);

        //본인이 수신자이고 읽지 않은 메시지 읽음 처리
        List<MongoChatMessage> unreadMessages = messages.stream()
                .filter(m -> m.getReceiverId().equals(userId) && !m.isRead())
                .peek(m -> m.setRead(true))
                .toList();

        //읽음 처리된 메시지 저장
        chatMongoRepository.saveAll(unreadMessages);

        return messages;
    }
}
