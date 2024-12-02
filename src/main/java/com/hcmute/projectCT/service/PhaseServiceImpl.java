package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.Phase.PhaseRequest;
import com.hcmute.projectCT.dto.Phase.PhaseResponse;
import com.hcmute.projectCT.dto.Phase.UpdatePhaseRequest;
import com.hcmute.projectCT.dto.Task.TaskResponse;
import com.hcmute.projectCT.enums.Status;
import com.hcmute.projectCT.model.*;
import com.hcmute.projectCT.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class PhaseServiceImpl implements PhaseService{
    final CollaboratorRepository collaboratorRepository;
    final PhaseRepository phaseRepository;
    final ProjectRepository projectRepository;
    final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public void createNewPhase(Long projectId, PhaseRequest phaseRequest) {
        Project project = projectRepository.findById(projectId).orElse(null);
        Phase phase = Phase
                .builder()
                .name(phaseRequest.getName())
                .description(phaseRequest.getDescription())
                .startDate(phaseRequest.getStartDate())
                .endDate(phaseRequest.getEndDate())
                .status(Status.TODO)
                .createdDate(LocalDateTime.now())
                .project(project)
                .build();
        phaseRepository.save(phase);
    }

    @Override
    public PhaseResponse getPhase(Long phaseId) {
        Phase phase = phaseRepository.findById(phaseId).orElse(null);
        return new PhaseResponse(phase);
    }

    @Override
    public List<PhaseResponse> getAllPhase(Long projectId) {
        List<Phase> phases = phaseRepository.findByProject_Id(projectId);
        List<PhaseResponse> phaseResponses = new ArrayList<>();
        for (Phase phase : phases) {
            phaseResponses.add(new PhaseResponse(phase));
        }
        return phaseResponses;
    }

    @Override
    public List<TaskResponse> getAllTaskInPhase(Long phaseId) {
        List<Task> tasks = phaseRepository.findById(phaseId).orElse(null).getTaskList();
        List<TaskResponse> taskResponses = new ArrayList<>();
        for (Task task : tasks) {
            taskResponses.add(new TaskResponse(task));
        }
        return taskResponses;
    }

    @Override
    public void updatePhase(Long phaseId, UpdatePhaseRequest updatePhaseRequest) {
        Phase phase = phaseRepository.findById(phaseId).orElse(null);
        phase.setName(updatePhaseRequest.getName());
        phase.setDescription(updatePhaseRequest.getDescription());
        phase.setStartDate(updatePhaseRequest.getStartDate());
        phase.setEndDate(updatePhaseRequest.getEndDate());
        phase.setStatus(updatePhaseRequest.getStatus());
        phaseRepository.save(phase);
    }

    @Override
    public void deletePhase(Long phaseId) {
        phaseRepository.deleteById(phaseId);
    }

    @Override
    public void assignTask(Long taskId, String assigneeUsername) {
        User assignee = userRepository.findByUsername(assigneeUsername);
        if (assignee == null) {
            throw new IllegalArgumentException("User with username " + assigneeUsername + " does not exist.");
        }
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            throw new IllegalArgumentException("Task with ID " + taskId + " does not exist.");
        }
        Project project = task.getPhase() != null ? task.getPhase().getProject() : task.getBacklog().getProject();
        Collaborator collaborator = collaboratorRepository.findByProjectAndUser(project, assignee).orElse(null);
        if (collaborator == null) {
            throw new IllegalArgumentException("No collaborator found for user " + assigneeUsername + " in project " + project.getId());
        }
        task.setAssignee(collaborator);
        taskRepository.save(task);
    }


    @Override
    public void updateTaskStatus(Long taskId, Status status) {
        Task task = taskRepository.findById(taskId).orElse(null);
        task.setStatus(status);
        taskRepository.save(task);
    }

    @Override
    public void moveTaskToBacklog(Long taskId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task.getAssignee()!=null) {
            throw new IllegalArgumentException("Can not move assigned task!");
        }
        Backlog backlog = task.getPhase().getProject().getBacklog();
        task.setPhase(null);
        task.setBacklog(backlog);
        if (task.getSubTask()!=null) {
            for (Task subtask : task.getSubTask()) {
                if (task.getAssignee()!=null) {
                    throw new IllegalArgumentException("Can not move assigned task!");
                }
                subtask.setPhase(null);
                subtask.setBacklog(backlog);
                taskRepository.save(subtask);
            }
        }
        taskRepository.save(task);
    }
}
