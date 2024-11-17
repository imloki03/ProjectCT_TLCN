package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.Project.ProjectResponse;
import com.hcmute.projectCT.dto.Project.UpdateProjectRequest;
import com.hcmute.projectCT.model.Backlog;
import com.hcmute.projectCT.model.Media;
import com.hcmute.projectCT.model.Project;
import com.hcmute.projectCT.model.User;
import com.hcmute.projectCT.repository.ProjectRepository;
import com.hcmute.projectCT.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{

    final UserRepository userRepository;
    final ProjectRepository projectRepository;
    @Override
    public ProjectResponse createNewProject(String ownerUsername, String projectName, String projectDescription) {
        User owner = userRepository.findByUsername(ownerUsername);
        long timestamp = System.currentTimeMillis();
        String shortString = Long.toString(timestamp, 36);
        Project project = Project.builder()
                .owner(owner)
                .name(projectName)
                .description(projectDescription)
                .urlName(projectName.replaceAll("[^a-zA-Z0-9]", "").toLowerCase()+shortString)
                .createdDate(LocalDateTime.now())
                .build();
        Media media = Media
                .builder()
                .project(project)
                .build();
        Backlog backlog = Backlog
                .builder()
                .project(project)
                .build();
        project.setMedia(media);
        project.setBacklog(backlog);
        projectRepository.save(project);
        return new ProjectResponse(project);
    }

    @Override
    public ProjectResponse getProject(Long projectId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null){
            return null;
        }
        return new ProjectResponse(project);
    }

    @Override
    public List<ProjectResponse> getAllProjectForUser(String username) {
        List<Project> projects = projectRepository.findByOwner_UsernameOrCollaboratorList_User_Username(username, username);
        List<ProjectResponse> projectResponses = new ArrayList<>();
        for (Project project : projects) {
            projectResponses.add(new ProjectResponse(project));
        }
        return projectResponses;
    }

    @Override
    public void updateProjectAvatar(Long projectId, String avatarUrl) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project==null) {
            return;
        }
        project.setAvatarURL(avatarUrl);
        projectRepository.save(project);
    }

    @Override
    public void updateProjectInformation(Long projectId, UpdateProjectRequest info) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project==null) {
            return;
        }
        project.setName(info.getName());
        project.setDescription(info.getDescription());
        project.setUrlName(info.getUrlName());
        projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }
}
