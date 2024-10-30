package com.hcmute.projectCT.dto.User;

import com.hcmute.projectCT.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String username;
    private String name;
    private String email;
    private String gender;
    private String avatarURL;
    private UserStatusResponse status;
    private List<TagResponse> tagList;

    public UserResponse(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.avatarURL = user.getAvatarURL();
        this.status = new UserStatusResponse(user.getStatus().isActivated(), user.getStatus().isNew());
        this.tagList = user.getTagList().stream()
                .map(tag -> new TagResponse(tag.getId(), tag.getName(), tag.getType().name(), tag.getDescription()))
                .collect(Collectors.toList());
    }
}
