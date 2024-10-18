package com.hcmute.projectCT.dto.Project;

import com.hcmute.projectCT.model.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    private Long id;
    private String ownerUsername;
    private String name;
    private String description;
    private String urlName;
    private String avatarURL;
    private LocalDateTime createdDate;

    public ProjectResponse(Project project){
        this.id = project.getId();
        this.ownerUsername = project.getOwner().getUsername();
        this.name = project.getName();
        this.description = project.getDescription();
        this.urlName = project.getUrlName();
        this.avatarURL = project.getAvatarURL();
        this.createdDate = project.getCreatedDate();
    }
}
