package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.Task.TaskRequest;
import com.hcmute.projectCT.dto.Task.TaskResponse;
import com.hcmute.projectCT.dto.Task.UpdateTaskRequest;
import com.hcmute.projectCT.enums.Status;
import com.hcmute.projectCT.model.Phase;
import com.hcmute.projectCT.model.Project;
import com.hcmute.projectCT.model.Task;
import com.hcmute.projectCT.repository.PhaseRepository;
import com.hcmute.projectCT.repository.ProjectRepository;
import com.hcmute.projectCT.repository.TaskRepository;
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
public class BacklogServiceImpl implements BacklogService{
    final TaskRepository taskRepository;
    final UserRepository userRepository;
    final PhaseRepository phaseRepository;
    final ProjectRepository projectRepository;
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
            taskResponses.add(new TaskResponse(task));
        }
        return taskResponses;
    }

    @Override
    public void updateTask(Long taskId, UpdateTaskRequest req) {
        Task task = taskRepository.findById(taskId).orElse(null);
        task.setName(req.getName());
        task.setType(req.getType());
        task.setDescription(req.getDescription());
        task.setStartTime(req.getStartTime());
        task.setEndTime(req.getEndTime());
        task.setPriority(req.getPriority());
        task.setStatus(req.getStatus());
        if (req.getAssigneeUsername() != null){
            task.setAssignee(userRepository.findByUsername(req.getAssigneeUsername()));
        }
        taskRepository.save(task);
    }

    @Override
    public void moveTaskToPhase(Long taskId, Long phaseId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        Phase phase = phaseRepository.findById(phaseId).orElse(null);
        task.setPhase(phase);
        task.setBacklog(null);
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
