package com.hcmute.projectCT.dto.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditUserAvatarRequest {
    @Schema(description = "URL of the user's avatar", example = "https://example.com/avatar/johndoe.png")
    private String avatarURL;
}
