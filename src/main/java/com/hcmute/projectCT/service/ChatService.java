package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.Message.MessageRequest;
import com.hcmute.projectCT.dto.Message.MessageResponse;
import com.hcmute.projectCT.dto.Message.PinMessageRequest;
import com.hcmute.projectCT.model.Message;

import java.util.List;

public interface ChatService {
    public void sendMessage(MessageRequest request);
    public void pinMessage(PinMessageRequest request);
    public List<MessageResponse> getMessagesByProject(Long projectId);
    public List<MessageResponse> searchMessages(Long projectId, String keyword);
    public Message toEntity(MessageRequest request);
}
