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
public class PinMessageRequest {

    private Long pinMessageId;

    @Schema(description = "Username of the one who pin this message", example = "john_doe")
    private String pinSender;

    @Schema(description = "Project ID associated with the message", example = "10")
    private Long project;
}
