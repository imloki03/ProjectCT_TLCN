package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.Project.ProjectResponse;
import com.hcmute.projectCT.dto.Project.UpdateProjectRequest;
import com.hcmute.projectCT.model.Project;

import java.util.List;

public interface ProjectService {
    ProjectResponse createNewProject(String ownerUsername, String projectName, String projectDescription);
    ProjectResponse getProject(Long projectId);
    List<ProjectResponse> getAllProjectForUser(String username);
    void updateProjectAvatar(Long projectId, String avatarUrl);
    void updateProjectInformation(Long projectId, UpdateProjectRequest info);
    void deleteProject(Long projectId);
}
