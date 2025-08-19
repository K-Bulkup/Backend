package com.kbulkup.chat.service;

import com.kbulkup.chat.domain.MongoChatMessage;
import com.kbulkup.chat.dto.ChatMessageDTO;
import com.kbulkup.chat.dto.ChatSummaryDTO;
import com.kbulkup.chat.repository.ChatMongoRepository;
import com.kbulkup.common.exception.CounselingException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.counseling.domain.CounselingReservation;
import com.kbulkup.counseling.domain.ReservationStatus;
import com.kbulkup.counseling.mapper.CounselingReservationMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

/**
 * ChatServiceImpl Unit Test
 * This class tests the core business logic of chat service:
 * - Chat message saving and processing
 * - Chat room message retrieval
 * - Message read status handling
 * - Reservation status validation
 * - Receiver ID calculation
 * - Unread message counting
 */
@ExtendWith(MockitoExtension.class)
class ChatServiceImplTest {

    @Mock
    private ChatMongoRepository chatMongoRepository;

    @Mock
    private CounselingReservationMapper counselingReservationMapper;

    @InjectMocks
    private ChatServiceImpl chatService;

    @Test
    @DisplayName("채팅 메시지 저장 - 트레이니가 보낸 메시지 성공")
    void saveChatMessage_TraineeSender_Success() {
        // Given
        String roomId = "room_123";
        String traineeId = "1";
        String trainerId = "2";
        LocalDateTime sendTime = LocalDateTime.now();

        ChatMessageDTO dto = createChatMessageDTO(roomId, traineeId, "안녕하세요!", sendTime);

        CounselingReservation reservation = CounselingReservation.builder()
                .reservationId(1L)
                .traineeId(1L)
                .trainerId(2L)
                .status(ReservationStatus.ACTIVE.getName())
                .roomId(roomId)
                .build();

        int unreadCount = 3;

        given(counselingReservationMapper.findByRoomId(roomId)).willReturn(reservation);
        given(chatMongoRepository.countUnreadMessages(roomId, trainerId)).willReturn((long) unreadCount);

        // When
        ChatSummaryDTO result = chatService.saveChatMessage(dto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getRoomId()).isEqualTo(roomId);
        assertThat(result.getLastMessage()).isEqualTo("안녕하세요!");
        assertThat(result.getReceiverId()).isEqualTo(trainerId);
        assertThat(result.getSendAt()).isEqualTo(sendTime);
        assertThat(result.getUnreadCount()).isEqualTo(unreadCount);

        // MongoDB 저장 검증
        ArgumentCaptor<MongoChatMessage> messageCaptor = ArgumentCaptor.forClass(MongoChatMessage.class);
        then(chatMongoRepository).should().save(messageCaptor.capture());

        MongoChatMessage savedMessage = messageCaptor.getValue();
        assertThat(savedMessage.getRoomId()).isEqualTo(roomId);
        assertThat(savedMessage.getSenderId()).isEqualTo(traineeId);
        assertThat(savedMessage.getReceiverId()).isEqualTo(trainerId);
        assertThat(savedMessage.getMessage()).isEqualTo("안녕하세요!");
        assertThat(savedMessage.isRead()).isFalse();
        assertThat(savedMessage.getSendAt()).isEqualTo(sendTime);

        // MySQL 업데이트 검증
        then(counselingReservationMapper).should().updateLatestMessage(roomId, "안녕하세요!", sendTime);

        // Mock 호출 검증
        then(counselingReservationMapper).should().findByRoomId(roomId);
        then(chatMongoRepository).should().countUnreadMessages(roomId, trainerId);
    }

    @Test
    @DisplayName("채팅 메시지 저장 - 트레이너가 보낸 메시지 성공")
    void saveChatMessage_TrainerSender_Success() {
        // Given
        String roomId = "room_456";
        String traineeId = "1";
        String trainerId = "2";
        LocalDateTime sendTime = LocalDateTime.now();

        ChatMessageDTO dto = createChatMessageDTO(roomId, trainerId, "네, 반갑습니다!", sendTime);

        CounselingReservation reservation = CounselingReservation.builder()
                .reservationId(2L)
                .traineeId(1L)
                .trainerId(2L)
                .status(ReservationStatus.ACTIVE.getName())
                .roomId(roomId)
                .build();

        int unreadCount = 1;

        given(counselingReservationMapper.findByRoomId(roomId)).willReturn(reservation);
        given(chatMongoRepository.countUnreadMessages(roomId, traineeId)).willReturn((long) unreadCount);

        // When
        ChatSummaryDTO result = chatService.saveChatMessage(dto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getRoomId()).isEqualTo(roomId);
        assertThat(result.getLastMessage()).isEqualTo("네, 반갑습니다!");
        assertThat(result.getReceiverId()).isEqualTo(traineeId); // 트레이너가 보냈으므로 수신자는 트레이니
        assertThat(result.getSendAt()).isEqualTo(sendTime);
        assertThat(result.getUnreadCount()).isEqualTo(unreadCount);

        // MongoDB 저장 검증
        ArgumentCaptor<MongoChatMessage> messageCaptor = ArgumentCaptor.forClass(MongoChatMessage.class);
        then(chatMongoRepository).should().save(messageCaptor.capture());

        MongoChatMessage savedMessage = messageCaptor.getValue();
        assertThat(savedMessage.getSenderId()).isEqualTo(trainerId);
        assertThat(savedMessage.getReceiverId()).isEqualTo(traineeId);

        // Mock 호출 검증
        then(chatMongoRepository).should().countUnreadMessages(roomId, traineeId);
    }

    @Test
    @DisplayName("채팅 메시지 저장 - 비활성 예약 상태로 예외 발생")
    void saveChatMessage_InactiveReservation_ThrowsException() {
        // Given
        String roomId = "room_789";
        String senderId = "1";
        ChatMessageDTO dto = createChatMessageDTO(roomId, senderId, "메시지", LocalDateTime.now());

        CounselingReservation reservation = CounselingReservation.builder()
                .reservationId(3L)
                .traineeId(1L)
                .trainerId(2L)
                .status(ReservationStatus.COMPLETED.getName()) // ACTIVE가 아닌 상태
                .roomId(roomId)
                .build();

        given(counselingReservationMapper.findByRoomId(roomId)).willReturn(reservation);

        // When & Then
        assertThatThrownBy(() -> chatService.saveChatMessage(dto))
                .isInstanceOf(CounselingException.class)
                .hasFieldOrPropertyWithValue("responseCode", ResponseCode.CHAT_NOT_AVAILABLE);

        // 채팅 저장이 실행되지 않았는지 검증
        then(chatMongoRepository).should(never()).save(any(MongoChatMessage.class));
        then(counselingReservationMapper).should(never()).updateLatestMessage(anyString(), anyString(), any(LocalDateTime.class));
    }

    @Test
    @DisplayName("채팅방 메시지 조회 - 성공")
    void getMessagesByRoomId_Success() {
        // Given
        String roomId = "room_111";
        String userId = "1";

        List<MongoChatMessage> mockMessages = Arrays.asList(
                createMongoChatMessage("msg1", roomId, "1", "2", "첫 번째 메시지", false),
                createMongoChatMessage("msg2", roomId, "2", "1", "두 번째 메시지", false), // userId가 수신자이고 읽지 않음
                createMongoChatMessage("msg3", roomId, "2", "1", "세 번째 메시지", false)  // userId가 수신자이고 읽지 않음
        );

        given(chatMongoRepository.findByRoomId(roomId)).willReturn(mockMessages);

        // When
        List<MongoChatMessage> result = chatService.getMessagesByRoomId(roomId, userId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getMessage()).isEqualTo("첫 번째 메시지");
        assertThat(result.get(1).getMessage()).isEqualTo("두 번째 메시지");
        assertThat(result.get(2).getMessage()).isEqualTo("세 번째 메시지");

        // 읽음 처리 검증 (markAsRead 호출됨)
        then(chatMongoRepository).should(times(2)).findByRoomId(roomId); // getMessages와 markAsRead에서 각각 호출
        then(chatMongoRepository).should().saveAll(anyList()); // 읽음 처리된 메시지 저장 (msg2, msg3)
    }

    @Test
    @DisplayName("채팅방 메시지 조회 - 빈 리스트")
    void getMessagesByRoomId_EmptyList_Success() {
        // Given
        String roomId = "empty_room";
        String userId = "1";

        given(chatMongoRepository.findByRoomId(roomId)).willReturn(Collections.emptyList());

        // When
        List<MongoChatMessage> result = chatService.getMessagesByRoomId(roomId, userId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        // Mock 호출 검증
        then(chatMongoRepository).should(times(2)).findByRoomId(roomId);
        then(chatMongoRepository).should(never()).saveAll(anyList());
    }

    @Test
    @DisplayName("채팅방 메시지 조회 - 읽을 메시지 없음")
    void getMessagesByRoomId_NoUnreadMessages_Success() {
        // Given
        String roomId = "room_no_unread";
        String userId = "1";

        List<MongoChatMessage> mockMessages = Arrays.asList(
                createMongoChatMessage("msg1", roomId, "1", "2", "내가 보낸 메시지", false),
                createMongoChatMessage("msg2", roomId, "2", "1", "이미 읽은 메시지", true),
                createMongoChatMessage("msg3", roomId, "1", "2", "또 내가 보낸 메시지", false)
        );

        given(chatMongoRepository.findByRoomId(roomId)).willReturn(mockMessages);

        // When
        List<MongoChatMessage> result = chatService.getMessagesByRoomId(roomId, userId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);

        // 읽지 않은 메시지가 없으므로 saveAll이 호출되지 않아야 함
        then(chatMongoRepository).should(times(2)).findByRoomId(roomId);
        then(chatMongoRepository).should(never()).saveAll(anyList());
    }

    @Test
    @DisplayName("메시지 읽음 처리 - 성공")
    void markMessagesAsRead_Success() {
        // Given
        String roomId = "room_read";
        String userId = "1";

        List<MongoChatMessage> messages = Arrays.asList(
                createMongoChatMessage("msg1", roomId, "2", "1", "읽지 않은 메시지1", false),
                createMongoChatMessage("msg2", roomId, "1", "2", "내가 보낸 메시지", false),
                createMongoChatMessage("msg3", roomId, "2", "1", "읽지 않은 메시지2", false),
                createMongoChatMessage("msg4", roomId, "2", "1", "이미 읽은 메시지", true)
        );

        given(chatMongoRepository.findByRoomId(roomId)).willReturn(messages);

        // When
        chatService.MarkMessagesAsRead(roomId, userId);

        // Then
        // 읽지 않은 메시지들이 읽음 처리되어 저장되었는지 검증
        ArgumentCaptor<List<MongoChatMessage>> messagesCaptor = ArgumentCaptor.forClass(List.class);
        then(chatMongoRepository).should().saveAll(messagesCaptor.capture());

        List<MongoChatMessage> savedMessages = messagesCaptor.getValue();
        
        // userId가 수신자이고 읽지 않은 메시지들만 처리되어야 함
        assertThat(savedMessages).hasSize(2); // msg1, msg3만 해당
        savedMessages.forEach(message -> {
            assertThat(message.getReceiverId()).isEqualTo(userId);
            assertThat(message.isRead()).isTrue();
        });

        // Mock 호출 검증
        then(chatMongoRepository).should().findByRoomId(roomId);
    }

    @Test
    @DisplayName("메시지 읽음 처리 - 읽지 않은 메시지 없음")
    void markMessagesAsRead_NoUnreadMessages_Success() {
        // Given
        String roomId = "room_all_read";
        String userId = "1";

        List<MongoChatMessage> messages = Arrays.asList(
                createMongoChatMessage("msg1", roomId, "2", "1", "이미 읽은 메시지1", true),
                createMongoChatMessage("msg2", roomId, "1", "2", "내가 보낸 메시지", false),
                createMongoChatMessage("msg3", roomId, "2", "1", "이미 읽은 메시지2", true)
        );

        given(chatMongoRepository.findByRoomId(roomId)).willReturn(messages);

        // When
        chatService.MarkMessagesAsRead(roomId, userId);

        // Then
        // 읽지 않은 메시지가 없으므로 saveAll이 호출되지 않아야 함
        then(chatMongoRepository).should(never()).saveAll(anyList());
        then(chatMongoRepository).should().findByRoomId(roomId);
    }

    @Test
    @DisplayName("수신자 ID 계산 - 트레이니가 발신자인 경우")
    void saveChatMessage_TraineeAsSender_CalculatesCorrectReceiverId() {
        // Given
        String roomId = "room_calculation";
        Long traineeId = 100L;
        Long trainerId = 200L;
        String traineeIdStr = traineeId.toString();

        ChatMessageDTO dto = createChatMessageDTO(roomId, traineeIdStr, "테스트 메시지", LocalDateTime.now());

        CounselingReservation reservation = CounselingReservation.builder()
                .traineeId(traineeId)
                .trainerId(trainerId)
                .status(ReservationStatus.ACTIVE.getName())
                .roomId(roomId)
                .build();

        given(counselingReservationMapper.findByRoomId(roomId)).willReturn(reservation);
        given(chatMongoRepository.countUnreadMessages(eq(roomId), eq(trainerId.toString()))).willReturn(0L);

        // When
        ChatSummaryDTO result = chatService.saveChatMessage(dto);

        // Then
        assertThat(result.getReceiverId()).isEqualTo(trainerId.toString());

        // MongoDB 저장된 메시지의 수신자 ID 확인
        ArgumentCaptor<MongoChatMessage> messageCaptor = ArgumentCaptor.forClass(MongoChatMessage.class);
        then(chatMongoRepository).should().save(messageCaptor.capture());

        MongoChatMessage savedMessage = messageCaptor.getValue();
        assertThat(savedMessage.getReceiverId()).isEqualTo(trainerId.toString());
    }

    @Test
    @DisplayName("수신자 ID 계산 - 트레이너가 발신자인 경우")
    void saveChatMessage_TrainerAsSender_CalculatesCorrectReceiverId() {
        // Given
        String roomId = "room_calculation2";
        Long traineeId = 300L;
        Long trainerId = 400L;
        String trainerIdStr = trainerId.toString();

        ChatMessageDTO dto = createChatMessageDTO(roomId, trainerIdStr, "트레이너 메시지", LocalDateTime.now());

        CounselingReservation reservation = CounselingReservation.builder()
                .traineeId(traineeId)
                .trainerId(trainerId)
                .status(ReservationStatus.ACTIVE.getName())
                .roomId(roomId)
                .build();

        given(counselingReservationMapper.findByRoomId(roomId)).willReturn(reservation);
        given(chatMongoRepository.countUnreadMessages(eq(roomId), eq(traineeId.toString()))).willReturn(2L);

        // When
        ChatSummaryDTO result = chatService.saveChatMessage(dto);

        // Then
        assertThat(result.getReceiverId()).isEqualTo(traineeId.toString());
        assertThat(result.getUnreadCount()).isEqualTo(2);

        // MongoDB 저장된 메시지의 수신자 ID 확인
        ArgumentCaptor<MongoChatMessage> messageCaptor = ArgumentCaptor.forClass(MongoChatMessage.class);
        then(chatMongoRepository).should().save(messageCaptor.capture());

        MongoChatMessage savedMessage = messageCaptor.getValue();
        assertThat(savedMessage.getReceiverId()).isEqualTo(traineeId.toString());
    }

    // 헬퍼 메서드들
    private ChatMessageDTO createChatMessageDTO(String roomId, String senderId, String message, LocalDateTime sendAt) {
        ChatMessageDTO dto = new ChatMessageDTO();
        ReflectionTestUtils.setField(dto, "roomId", roomId);
        ReflectionTestUtils.setField(dto, "senderId", senderId);
        ReflectionTestUtils.setField(dto, "message", message);
        dto.setSendAt(sendAt);
        return dto;
    }

    private MongoChatMessage createMongoChatMessage(String id, String roomId, String senderId, String receiverId, String message, boolean isRead) {
        return MongoChatMessage.builder()
                .id(id)
                .roomId(roomId)
                .senderId(senderId)
                .receiverId(receiverId)
                .message(message)
                .isRead(isRead)
                .sendAt(LocalDateTime.now())
                .build();
    }
}
