package com.hcmute.projectCT.dto.Message;

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
    private String sender;
    private String content;
    private String project;
    private LocalDateTime sentTime;
    private boolean isPinned;
}
