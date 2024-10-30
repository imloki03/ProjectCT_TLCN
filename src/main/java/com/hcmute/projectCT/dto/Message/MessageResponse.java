package com.hcmute.projectCT.dto.Message;

import com.hcmute.projectCT.model.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private Long id;
    private String sender;
    private String content;
    private String project;
    private LocalDateTime sentTime;
    private boolean isPinned;

    public MessageResponse(Message message) {
        this.id = message.getId();
        this.sender = message.getSender().getUser().getUsername();
        this.content = message.getContent();
        this.project = message.getProject().getName();
        this.sentTime = message.getSentTime();
        this.isPinned = message.isPinned();
    }
}
