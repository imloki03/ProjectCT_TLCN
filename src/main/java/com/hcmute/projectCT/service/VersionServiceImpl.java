package com.hcmute.projectCT.service;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.Task.TaskResponse;
import com.hcmute.projectCT.dto.Version.VersionRequest;
import com.hcmute.projectCT.dto.Version.VersionResponse;
import com.hcmute.projectCT.enums.Status;
import com.hcmute.projectCT.exception.InternalServerException;
import com.hcmute.projectCT.model.Project;
import com.hcmute.projectCT.model.Task;
import com.hcmute.projectCT.model.Version;
import com.hcmute.projectCT.repository.PhaseRepository;
import com.hcmute.projectCT.repository.ProjectRepository;
import com.hcmute.projectCT.repository.TaskRepository;
import com.hcmute.projectCT.repository.VersionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class VersionServiceImpl implements VersionService {
    private final VersionRepository versionRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final PhaseRepository phaseRepository;

    @Override
    public void createVersion(VersionRequest versionRequest, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new InternalServerException(HttpStatus.NOT_FOUND.value(), MessageKey.PROJECT_NOT_FOUND));

        try {
            Version version = toEntity(versionRequest);
            version.setProject(project);
            version.getTaskList().forEach(task -> {task.setVersion(version);});
            taskRepository.saveAll(version.getTaskList());
            versionRepository.save(version);
        } catch (Exception e) {
            log.error("Error occurred while creating version", e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public void editVersion(Long id, VersionRequest versionRequest) {
        Version existingVersion = versionRepository.findById(id)
                .orElseThrow(() -> new InternalServerException(HttpStatus.NOT_FOUND.value(), MessageKey.VERSION_NOT_FOUND));

        try {
            existingVersion.getTaskList().forEach(task -> {task.setVersion(null);});
            taskRepository.saveAll(existingVersion.getTaskList());
            Version updatedVersion = toEntity(existingVersion, versionRequest);
            updatedVersion.getTaskList().forEach(task -> {task.setVersion(updatedVersion);});
            taskRepository.saveAll(updatedVersion.getTaskList());
            versionRepository.save(updatedVersion);
        } catch (Exception e) {
            log.error("Error occurred while editing version", e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public void deleteVersion(Long id) {
        Version version = versionRepository.findById(id)
                .orElseThrow(() -> new InternalServerException(HttpStatus.NOT_FOUND.value(), MessageKey.VERSION_NOT_FOUND));

        try {
            versionRepository.delete(version);
        } catch (Exception e) {
            log.error("Error occurred while deleting version with ID: {}", id, e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public VersionResponse getVersionById(Long id) {
        Version version = versionRepository.findById(id)
                .orElseThrow(() -> new InternalServerException(HttpStatus.NOT_FOUND.value(), MessageKey.VERSION_NOT_FOUND));

        try {
            return new VersionResponse(version);
        } catch (Exception e) {
            log.error("Error occurred while fetching version info for ID: {}", id, e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public List<VersionResponse> getVersionsByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new InternalServerException(HttpStatus.NOT_FOUND.value(), MessageKey.PROJECT_NOT_FOUND));

        try {
            List<Version> versions = versionRepository.findByProject(project);
            return versions.stream().map(VersionResponse::new).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while fetching versions for project ID: {}", projectId, e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public List<TaskResponse> getAvailableTasksInPhase(Long phaseId) {
        try {
            List<Task> tasks = Objects.requireNonNull(phaseRepository.findById(phaseId).orElse(null)).getTaskList()
                    .stream()
                    .filter(t -> (t.getVersion() == null && t.getStatus() != Status.DONE))
                    .toList();
            return tasks.stream().map(TaskResponse::new).collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public List<TaskResponse> getAvailableTasksInBacklog(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new InternalServerException(HttpStatus.NOT_FOUND.value(), MessageKey.PROJECT_NOT_FOUND));

        try {
            List<Task> tasks = Objects.requireNonNull(project.getBacklog().getTaskList())
                    .stream()
                    .filter(t -> (t.getVersion() == null && t.getStatus() != Status.DONE))
                    .toList();
            return tasks.stream().map(TaskResponse::new).collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public Version toEntity(VersionRequest versionRequest) {
        return Version.builder()
                .name(versionRequest.getName())
                .description(versionRequest.getDescription())
                .createdDate(LocalDateTime.now())
                .taskList(versionRequest.getTargetedTaskList().stream()
                        .flatMap(taskId -> {
                            Task task = taskRepository.findById(taskId)
                                    .orElseThrow(() -> new InternalServerException(
                                            HttpStatus.NOT_FOUND.value(), MessageKey.TASK_NOT_FOUND));
                            return getTaskAndSubtasks(task).stream();
                        })
                        .collect(Collectors.toList()))
                .build();
    }

    private List<Task> getTaskAndSubtasks(Task task) {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task); // Add the main task

        // Recursively add subtasks
        if (task.getSubTask() != null && !task.getSubTask().isEmpty()) {
            for (Task subTask : task.getSubTask()) {
                tasks.addAll(getTaskAndSubtasks(subTask));
            }
        }

        return tasks;
    }

    @Override
    public Version toEntity(Version existingVersion, VersionRequest versionRequest) {
        List<Task> updatedTaskList = versionRequest.getTargetedTaskList() != null
                ? versionRequest.getTargetedTaskList().stream()
                                .map(taskId -> taskRepository.findById(taskId)
                                .orElseThrow(() -> new InternalServerException(
                                HttpStatus.NOT_FOUND.value(), MessageKey.TASK_NOT_FOUND)))
                                .collect(Collectors.toList())
                : existingVersion.getTaskList();

        return existingVersion.toBuilder()
                .name(Optional.ofNullable(versionRequest.getName()).orElse(existingVersion.getName()))
                .description(Optional.ofNullable(versionRequest.getDescription()).orElse(existingVersion.getDescription()))
                .taskList(updatedTaskList)
                .build();
    }
}
