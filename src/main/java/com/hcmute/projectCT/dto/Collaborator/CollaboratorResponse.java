package com.hcmute.projectCT.dto.Collaborator;

import com.hcmute.projectCT.enums.Permission;
import com.hcmute.projectCT.model.Collaborator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollaboratorResponse {
    Long id;
    Long userId;
    String username;
    String name;
    String avatarURL;
    Permission permission;

    public CollaboratorResponse(Collaborator collaborator) {
        this.id = collaborator.getId();
        this.userId = collaborator.getUser().getId();
        this.username = collaborator.getUser().getUsername();
        this.name = collaborator.getUser().getName();
        this.avatarURL = collaborator.getUser().getAvatarURL();
        this.permission = collaborator.getPermission();
    }
}
