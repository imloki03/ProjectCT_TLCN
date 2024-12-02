package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.Message.MessageRequest;
import com.hcmute.projectCT.dto.Message.MessageResponse;
import com.hcmute.projectCT.dto.Message.PinMessageRequest;
import com.hcmute.projectCT.model.Message;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChatService {
    public void sendMessage(MessageRequest request);
    public void pinMessage(PinMessageRequest request);
    public Page<MessageResponse> getMessagesByProject(Long projectId, int page, int size);
    public List<MessageResponse> searchMessages(Long projectId, String keyword);
    public Message toEntity(MessageRequest request);
    public List<MessageResponse> getPinnedMessagesByProject(Long projectId);
}
