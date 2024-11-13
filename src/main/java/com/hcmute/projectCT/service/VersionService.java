package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.Task.TaskResponse;
import com.hcmute.projectCT.dto.Version.VersionRequest;
import com.hcmute.projectCT.dto.Version.VersionResponse;
import com.hcmute.projectCT.model.Version;

import java.util.List;

public interface VersionService {

    void createVersion(VersionRequest versionRequest, Long projectId);

    void editVersion(Long id, VersionRequest versionRequest);

    void deleteVersion(Long id);

    VersionResponse getVersionById(Long id);

    List<VersionResponse> getVersionsByProject(Long projectId);

    Version toEntity(VersionRequest versionRequest);

    Version toEntity(Version existingVersion, VersionRequest versionRequest);

    List<TaskResponse> getAvailableTasksInPhase(Long projectId);
}
