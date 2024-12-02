package com.hcmute.projectCT.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.Message.MessageRequest;
import com.hcmute.projectCT.dto.Message.MessageResponse;
import com.hcmute.projectCT.dto.Message.PinMessageRequest;
import com.hcmute.projectCT.dto.RespondData;
import com.hcmute.projectCT.exception.InternalServerException;
import com.hcmute.projectCT.model.Message;
import com.hcmute.projectCT.service.ChatService;
import com.hcmute.projectCT.util.MessageUtil;
import com.hcmute.projectCT.util.WebUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
@Tag(name = "Chat Controller")
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageUtil messageUtil;
    private final MessageSource messageSource;

    @Operation(
            summary = "Send a message",
            description = "This API sends a message to the public chat",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Message sent successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error, check for NULL, Data, ...")
            })
    @MessageMapping("/send")
    public void sendMessage(MessageRequest request) {
        try {
            chatService.sendMessage(request);
        } catch (InternalServerException e) {
            messagingTemplate.convertAndSend("/private/" + request.getSender(), messageSource.getMessage(e.getMessage(), new Object[]{}, Locale.ENGLISH));
        }
    }


    @Operation(
            summary = "Pin a message",
            description = "This API allows a user to pin a message",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Message pinned successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error, check for NULL, Data, ...")
            })
    @MessageMapping("/pin")
    public void pinMessage(PinMessageRequest request) {
        try {
            chatService.pinMessage(request);
        } catch (InternalServerException e) {
            messagingTemplate.convertAndSend("/private/" + request.getPinSender(), messageSource.getMessage(e.getMessage(), new Object[]{}, Locale.ENGLISH));
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
    public ResponseEntity<?> getMessagesByProject(
            @Parameter(description = "The id of the project that messages going to be fetched")
            @PathVariable Long projectId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            Page<MessageResponse> messages = chatService.getMessagesByProject(projectId, page, size);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.MESSAGES_RETRIEVED_SUCCESS))
                    .data(messages) // Include pagination metadata if needed
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        } catch (InternalServerException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Get messages by project",
            description = "This API fetches all the messages for a specific project",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Messages retrieved successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error, check for NULL, Data, ...")
            })
    @GetMapping("/pin/project/{projectId}")
    public ResponseEntity<?> getPinnedMessagesByProject(
            @Parameter(description = "The id of the project that messages going to be fetched")
            @PathVariable Long projectId) {
        try {
            List<MessageResponse> messages = chatService.getPinnedMessagesByProject(projectId);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.MESSAGES_RETRIEVED_SUCCESS))
                    .data(messages) // Include pagination metadata if needed
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        } catch (InternalServerException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Search messages",
            description = "This API searches for messages containing the specified keyword",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Messages found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error, check for NULL, Data, ...")
            })
    @GetMapping("/project/{projectId}/search")
    public ResponseEntity<?> searchMessages(@Parameter(description = "The id of the project that messages going to be fetched")
                                            @PathVariable Long projectId,
                                            @Parameter(description = "The keyword to search for")
                                            @RequestParam String keyword) {
        try {
            List<MessageResponse> messages = chatService.searchMessages(projectId, keyword);
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
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
    }

}
