package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.Collaborator.CollaboratorResponse;
import com.hcmute.projectCT.enums.Permission;

import java.util.List;

public interface CollaboratorService {
    void addNewCollaborator(Long projectId, String username);
    List<CollaboratorResponse> getAllCollaborators(Long projectId);
    void updateCollabPermission(Long collabId, Permission permission);
    void deleteCollaborator(Long collabId);
}
