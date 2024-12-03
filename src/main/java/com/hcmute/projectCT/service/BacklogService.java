package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.Task.TaskRequest;
import com.hcmute.projectCT.dto.Task.TaskResponse;
import com.hcmute.projectCT.dto.Task.UpdateTaskRequest;
import com.hcmute.projectCT.model.Task;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

public interface BacklogService {
    void createNewTask(Long projectId, TaskRequest taskReq);
    TaskResponse getTask(Long taskId);
    List<TaskResponse> getAllTasksInBacklogs(Long projectId);
    void updateTask(Long projectId, Long taskId, UpdateTaskRequest req) throws MessagingException, IOException;
    void moveTaskToPhase(Long taskId, Long phaseId);
    void deleteTask(Long taskId);
}
