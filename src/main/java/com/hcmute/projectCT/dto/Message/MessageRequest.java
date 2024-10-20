package com.hcmute.projectCT.dto.Message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {

    @Schema(description = "Username of the sender", example = "john_doe")
    private String sender;

    @Schema(description = "Content of the message", example = "Please review the latest changes")
    private String content;

    @Schema(description = "Project ID associated with the message", example = "10")
    private Long project;
}
