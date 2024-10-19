package com.hcmute.projectCT.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.Message.MessageRequest;
import com.hcmute.projectCT.dto.Message.MessageResponse;
import com.hcmute.projectCT.dto.RespondData;
import com.hcmute.projectCT.exception.InternalServerException;
import com.hcmute.projectCT.model.Message;
import com.hcmute.projectCT.service.ChatService;
import com.hcmute.projectCT.util.MessageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
@Tag(name = "Chat Controller")
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageUtil messageUtil;

    @Operation(
            summary = "Send a message",
            description = "This API sends a message to the public chat",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Message sent successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error, check for NULL, Data, ...")
            })
    @MessageMapping("/send")
    @SendTo("/public")
    public RespondData<Message> sendMessage(MessageRequest request) {
        try {
            var message = chatService.sendMessage(request);
            return RespondData.<Message>builder()
                    .status(HttpStatus.OK.value())
                    .data(message)
                    .build();
        } catch (InternalServerException e) {
            messagingTemplate.convertAndSend("/private/" + request.getSender(), e.getMessage());
            return RespondData.<Message>builder()
                    .status(e.getErrorCode())
                    .data(null)
                    .build();
        }
    }


    @Operation(
            summary = "Pin a message",
            description = "This API allows a user to pin a message",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Message pinned successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error, check for NULL, Data, ...")
            })
    @PatchMapping("/pin/{id}")
    public ResponseEntity<?> pinMessage(@PathVariable Long id) {
        try {
            chatService.pinMessage(id);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.MESSAGE_PINNED_SUCCESS))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        } catch (InternalServerException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get messages by project",
            description = "This API fetches all the messages for a specific project",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Messages retrieved successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error, check for NULL, Data, ...")
            })
    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getMessagesByProject(@PathVariable Long projectId) {
        try {
            List<MessageResponse> messages = chatService.getMessagesByProject(projectId);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.MESSAGES_RETRIEVED_SUCCESS))
                    .data(messages)
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        } catch (InternalServerException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Search messages",
            description = "This API searches for messages containing the specified keyword",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Messages found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error, check for NULL, Data, ...")
            })
    @GetMapping("/search")
    public ResponseEntity<?> searchMessages(@RequestParam String keyword) {
        try {
            List<MessageResponse> messages = chatService.searchMessages(keyword);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.MESSAGES_RETRIEVED_SUCCESS))
                    .data(messages)
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        } catch (InternalServerException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
