package com.kbulkup.chat.service;

import com.kbulkup.chat.dto.ChatMessageDTO;
import com.kbulkup.chat.domain.MongoChatMessage;
import com.kbulkup.chat.dto.ChatSummaryDTO;
import com.kbulkup.chat.repository.ChatMongoRepository;
import com.kbulkup.counseling.domain.Counseling;
import com.kbulkup.counseling.domain.CounselingStatus;
import com.kbulkup.counseling.mapper.CounselingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMongoRepository chatMongoRepository;
    private final CounselingMapper counselingMapper;

    @Override
    @Transactional
    public ChatSummaryDTO saveChatMessage(ChatMessageDTO dto) {

        Counseling counseling = counselingMapper.findByRoomId(dto.getRoomId());

        //만료된 채팅방에서 채팅시 CounselingStatus 수정 및 에러 메시지 반환
        if (counseling.getExpiresAt().isBefore(LocalDateTime.now())) {
            counselingMapper.updateStatusToExpired(dto.getRoomId(), CounselingStatus.EXPIRED.getName());
            return null;
        }

        String receiverId = dto.getSenderId().equals(counseling.getUserId().toString())
                ? counseling.getTrainerId().toString() : counseling.getUserId().toString();

        //mongodb 채팅 내역 저장
        MongoChatMessage document = MongoChatMessage.create(dto, receiverId);
        chatMongoRepository.save(document);

        //mysql 마지막 채팅 시간, 마지막 채팅 업데이트
        counselingMapper.updateLatestMessage(dto.getRoomId(), dto.getMessage(), dto.getSendAt());

        //채팅방 요약 정보 전송 위함
        int unreadCount = (int) chatMongoRepository.countUnreadMessages(dto.getRoomId(), receiverId);
        return ChatSummaryDTO.create(dto, unreadCount, receiverId);
    }

    @Override
    @Transactional
    public List<MongoChatMessage> getMessagesByRoomId(String roomId, String userId) {
        markAsRead(roomId, userId);
        return chatMongoRepository.findByRoomId(roomId);
    }

    @Override
    @Transactional
    public void MarkMessagesAsRead(String roomId, String userId) {
        markAsRead(roomId, userId);
    }

    public void markAsRead(String roomId, String userId) {

        //본인이 수신자이고 읽지 않은 메시지 읽음 처리
        List<MongoChatMessage> unreadMessages = chatMongoRepository.findByRoomId(roomId).stream()
                .filter(m -> m.getReceiverId().equals(userId) && !m.isRead())
                .peek(m -> m.setRead(true))
                .toList();

        //읽음 처리된 메시지 저장
        if (!unreadMessages.isEmpty()) {
            chatMongoRepository.saveAll(unreadMessages);
        }
    }
}
