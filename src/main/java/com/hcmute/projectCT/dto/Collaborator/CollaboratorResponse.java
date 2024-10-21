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
    String userUsername;
    String userName;
    String userAvatarURL;
    Permission permission;

    public CollaboratorResponse(Collaborator collaborator) {
        this.id = collaborator.getId();
        this.userId = collaborator.getUser().getId();
        this.userUsername = collaborator.getUser().getUsername();
        this.userName = collaborator.getUser().getName();
        this.userAvatarURL = collaborator.getUser().getAvatarURL();
        this.permission = collaborator.getPermission();
    }
}
