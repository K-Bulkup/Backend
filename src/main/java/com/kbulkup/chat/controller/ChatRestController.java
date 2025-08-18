package com.kbulkup.chat.controller;

import com.kbulkup.chat.domain.MongoAiChatMessage;
import com.kbulkup.chat.domain.MongoChatMessage;
import com.kbulkup.chat.dto.AiChatHistoryResponseDTO;
import com.kbulkup.chat.dto.ReadMessageDTO;
import com.kbulkup.chat.service.AiChatService;
import com.kbulkup.chat.service.ChatService;
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Swagger
import io.swagger.annotations.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Chat", description = "채팅 이력 / 읽음 처리 / AI 채팅 이력 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common/chats")
public class ChatRestController {

    private final ChatService chatService;
    private final AiChatService aiChatService;

    @ApiOperation(value = "채팅 이력 조회", notes = "roomId 기준으로 채팅 이력을 조회합니다.")
    @ApiImplicitParam(name = "roomId", value = "채팅방 ID", required = true,
            dataType = "string", paramType = "path", example = "ROOM-abc123")
    @ApiResponses({ @ApiResponse(code = 200, message = "성공") })
    @GetMapping("/{roomId}")
    public CustomResponse<List<MongoChatMessage>> getChatHistory(
            @PathVariable String roomId,
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {
        return CustomResponse.success(ResponseCode.SUCCESS,
                chatService.getMessagesByRoomId(roomId, String.valueOf(user.getUserId())));
    }

    @ApiOperation(value = "메시지 읽음 처리", notes = "해당 방에서 특정 사용자의 메시지를 읽음 처리합니다.")
    @ApiResponses({ @ApiResponse(code = 200, message = "성공") })
    @PostMapping("/read")
    public CustomResponse<Void> readMessages(
            @ApiParam(value = "읽음 처리 요청 바디", required = true)
            @RequestBody ReadMessageDTO dto) {
        chatService.MarkMessagesAsRead(dto.getRoomId(), dto.getUserId());
        return CustomResponse.success(ResponseCode.SUCCESS);
    }

    @ApiOperation(value = "AI 채팅 이력 조회", notes = "해당 사용자의 AI 채팅 이력을 조회합니다.")
    @ApiResponses({ @ApiResponse(code = 200, message = "성공") })
    @GetMapping("/ai")
    public CustomResponse<AiChatHistoryResponseDTO> getAiChatHistory(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {
        return CustomResponse.success(ResponseCode.SUCCESS,
                aiChatService.getAiMessagesByUserId(String.valueOf(user.getUserId())));
    }
}
