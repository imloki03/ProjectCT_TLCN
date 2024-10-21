package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.Phase.PhaseRequest;
import com.hcmute.projectCT.dto.Phase.PhaseResponse;
import com.hcmute.projectCT.dto.Phase.UpdatePhaseRequest;
import com.hcmute.projectCT.dto.Task.TaskResponse;
import com.hcmute.projectCT.enums.Status;

import java.util.List;

public interface PhaseService {
    void createNewPhase(Long projectId, PhaseRequest phaseRequest);
    PhaseResponse getPhase(Long phaseId);
    List<PhaseResponse> getAllPhase(Long projectId);
    List<TaskResponse> getAllTaskInPhase(Long phaseId);
    void updatePhase(Long phaseId, UpdatePhaseRequest updatePhaseRequest);
    void deletePhase(Long phaseId);
    void assignTask(Long taskId, String assigneeUsername);
    void updateTaskStatus(Long taskId, Status status);
    void moveTaskToBacklog(Long taskId);
}
