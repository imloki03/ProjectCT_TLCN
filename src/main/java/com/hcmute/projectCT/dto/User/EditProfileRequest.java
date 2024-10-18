package com.hcmute.projectCT.dto.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditProfileRequest {
    private String name;
    private String gender;
    private String avatarURL;
    private List<Long> tagList;
}
