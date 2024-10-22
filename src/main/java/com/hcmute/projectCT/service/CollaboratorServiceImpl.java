package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.Collaborator.CollaboratorResponse;
import com.hcmute.projectCT.enums.Permission;
import com.hcmute.projectCT.model.Collaborator;
import com.hcmute.projectCT.model.Project;
import com.hcmute.projectCT.model.User;
import com.hcmute.projectCT.repository.CollaboratorRepository;
import com.hcmute.projectCT.repository.ProjectRepository;
import com.hcmute.projectCT.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class CollaboratorServiceImpl implements CollaboratorService{
    final ProjectRepository projectRepository;
    final UserRepository userRepository;
    final CollaboratorRepository collaboratorRepository;

    @Override
    public void addNewCollaborator(Long projectId, String username) {
        Project project = projectRepository.findById(projectId).orElse(null);
        User user = userRepository.findByUsername(username);
        collaboratorRepository.save(Collaborator
                .builder()
                .project(project)
                .user(user)
                .permission(Permission.COLLAB)
                .build());
    }

    @Override
    public List<CollaboratorResponse> getAllCollaborators(Long projectId) {
        List<Collaborator> collaborators = collaboratorRepository.findByProject_Id(projectId);
        List<CollaboratorResponse> collaboratorResponses = new ArrayList<>();
        for (Collaborator collaborator: collaborators) {
            collaboratorResponses.add(new CollaboratorResponse(collaborator));
        }
        return collaboratorResponses;
    }

    @Override
    public void updateCollabPermission(Long collabId, Permission permission) {
        Collaborator collaborator = collaboratorRepository.findById(collabId).orElse(null);
        collaborator.setPermission(permission);
        collaboratorRepository.save(collaborator);
    }

    @Override
    public void deleteCollaborator(Long collabId) {
        collaboratorRepository.deleteById(collabId);
    }
}