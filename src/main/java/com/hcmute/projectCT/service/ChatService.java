package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.Message.MessageRequest;
import com.hcmute.projectCT.dto.Message.MessageResponse;
import com.hcmute.projectCT.model.Message;

import java.util.List;

public interface ChatService {
    public Message sendMessage(MessageRequest request);
    public void pinMessage(Long id);
    public List<MessageResponse> getMessagesByProject(Long projectId);
    public List<MessageResponse> searchMessages(String keyword);
    public Message toEntity(MessageRequest request);
}
