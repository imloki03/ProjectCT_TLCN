package com.hcmute.projectCT.dto.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String username;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String avatarURL;
    private UserStatusResponse status;
    private List<TagResponse> tagList;
}
