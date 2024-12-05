package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.Task.TaskRequest;
import com.hcmute.projectCT.dto.Task.TaskResponse;
import com.hcmute.projectCT.dto.Task.UpdateTaskRequest;
import com.hcmute.projectCT.enums.Status;
import com.hcmute.projectCT.model.*;
import com.hcmute.projectCT.repository.*;
import com.hcmute.projectCT.util.EmailUtil;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class BacklogServiceImpl implements BacklogService{
    final TaskRepository taskRepository;
    final CollaboratorRepository collaboratorRepository;
    final PhaseRepository phaseRepository;
    final ProjectRepository projectRepository;
    final EmailUtil emailUtil;
    @Override
    public void createNewTask(Long projectId, TaskRequest taskReq) {
        Project project = projectRepository.findById(projectId).orElse(null);
        Task task = Task
                .builder()
                .name(taskReq.getName())
                .type(taskReq.getType())
                .description(taskReq.getDescription())
                .priority(taskReq.getPriority())
                .status(Status.TODO)
                .backlog(project.getBacklog())
                .build();
        if (taskReq.getParentTaskId()!=null){
            Task parent = taskRepository.findById(taskReq.getParentTaskId()).orElse(null);
            task.setParentTask(parent);
        }
        taskRepository.save(task);
    }

    @Override
    public TaskResponse getTask(Long taskId) {
        return new TaskResponse(taskRepository.findById(taskId).orElse(null));
    }

    @Override
    public List<TaskResponse> getAllTasksInBacklogs(Long projectId) {
        List<Task> tasks = taskRepository.findByBacklog_Project_Id(projectId);
        List<TaskResponse> taskResponses = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getBacklog()!=null) {
                taskResponses.add(new TaskResponse(task));
            }
        }
        return taskResponses;
    }

    @Override
    public void updateTask(Long projectId, Long taskId, UpdateTaskRequest req) throws MessagingException, IOException {
        Task task = taskRepository.findById(taskId).orElse(null);
        task.setName(req.getName());
        task.setType(req.getType());
        task.setDescription(req.getDescription());
        task.setPriority(req.getPriority());
        if (req.getStartTime()!=null){
            task.setStartTime(req.getStartTime());
        }
        if (req.getEndTime()!=null){
            task.setEndTime(req.getEndTime());
        }
        if (req.getStatus()!=null){
            log.error("test"+req.getStatus()) ;
            task.setStatus(req.getStatus());
        }
        if (req.getAssigneeUsername()!=null && !req.getAssigneeUsername().trim().isEmpty()){
            Collaborator assignee = collaboratorRepository.findByProject_IdAndUser_Username(projectId, req.getAssigneeUsername());
            task.setAssignee(assignee);
            Project project = task.getPhase() != null ? task.getPhase().getProject() : task.getBacklog().getProject();
            String subject = "Assigned Task From "+project.getName();
            emailUtil.sendEmail(
                    assignee.getUser().getEmail(),
                    subject,
                    "assign-task-template.html",
                    assignee.getUser().getName(),
                    project.getName(),
                    task.getName(),
                    task.getDescription(),
                    task.getStartTime().toString(),
                    task.getEndTime().toString());
        }
        taskRepository.save(task);
    }

    @Override
    public void moveTaskToPhase(Long taskId, Long phaseId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        Phase phase = phaseRepository.findById(phaseId).orElse(null);
        task.setParentTask(null);
        moveAllSubtaskToPhase(task, phase);
        taskRepository.save(task);
    }

    public void moveAllSubtaskToPhase(Task task, Phase phase) {
        task.setPhase(phase);
        task.setBacklog(null);
        if (task.getSubTask() != null) {
            for (Task subTask : task.getSubTask()) {
                moveAllSubtaskToPhase(subTask, phase);
            }
        }
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
