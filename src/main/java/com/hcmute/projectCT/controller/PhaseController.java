package com.hcmute.projectCT.controller;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.Phase.PhaseRequest;
import com.hcmute.projectCT.dto.Phase.PhaseResponse;
import com.hcmute.projectCT.dto.Phase.UpdatePhaseRequest;
import com.hcmute.projectCT.dto.RespondData;
import com.hcmute.projectCT.dto.Task.TaskResponse;
import com.hcmute.projectCT.enums.Status;
import com.hcmute.projectCT.service.PhaseService;
import com.hcmute.projectCT.util.MessageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/phase")
@RequiredArgsConstructor
@Tag(name = "Phase Controller")
public class PhaseController {
    final PhaseService phaseService;
    final MessageUtil messageUtil;

    @Operation(
            summary = "Create new phase",
            description = "This API will create new phase for a project.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Phase created successfully"),
            })
    @PostMapping("{projectId}")
    public ResponseEntity<?> createNewPhase(@PathVariable Long projectId, @RequestBody PhaseRequest phaseRequest){
        phaseService.createNewPhase(projectId, phaseRequest);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.PHASE_CREATE_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Get phase",
            description = "This API will get a phase information by its ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get phase successfully"),
            })
    @GetMapping("{projectId}/{phaseId}")
    public ResponseEntity<?> getPhase(@PathVariable Long projectId, @PathVariable Long phaseId) {
        PhaseResponse phaseResponse = phaseService.getPhase(phaseId);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .data(phaseResponse)
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all phases",
            description = "This API will get all phases information of a project.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get all phases successfully"),
            })
    @GetMapping("{projectId}")
    public ResponseEntity<?> getAllPhase(@PathVariable Long projectId){
        List<PhaseResponse> phaseResponses = phaseService.getAllPhase(projectId);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .data(phaseResponses)
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all tasks in phase",
            description = "This API will get all tasks in phase.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get all tasks in phase successfully"),
            })
    @GetMapping("{projectId}/{phaseId}/tasks")
    public ResponseEntity<?> getAllTasksInPhase(@PathVariable Long projectId, @PathVariable Long phaseId){
        List<TaskResponse> taskResponses = phaseService.getAllTaskInPhase(phaseId);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .data(taskResponses)
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Update phase information",
            description = "This API will update phase information for a project.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update phase successfully"),
            })
    @PutMapping("{projectId}/{phaseId}")
    public ResponseEntity<?> updatePhase(@PathVariable Long projectId, @PathVariable Long phaseId, @RequestBody UpdatePhaseRequest updatePhaseRequest) {
        phaseService.updatePhase(phaseId, updatePhaseRequest);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.PHASE_UPDATE_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete phase",
            description = "This API will delete a phase.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Delete phase successfully"),
            })
    @DeleteMapping("{projectId}/{phaseId}")
    public ResponseEntity<?> deletePhase(@PathVariable Long projectId, @PathVariable Long phaseId) {
        phaseService.deletePhase(phaseId);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.PHASE_DELETE_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Assign task",
            description = "This API will assign a task to a collaborator.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Assign task successfully"),
            })
    @PatchMapping("{projectId}/{phaseId}/{taskId}/assign/{assigneeUsername}")
    public ResponseEntity<?> assignTask(@PathVariable Long projectId, @PathVariable Long phaseId, @PathVariable Long taskId, @PathVariable String assigneeUsername) {
        phaseService.assignTask(taskId, assigneeUsername);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Update task status",
            description = "This API will update a task's status.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update status successfully"),
            })
    @PatchMapping("{projectId}/{phaseId}/{taskId}/status/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable Long projectId, @PathVariable Long phaseId, @PathVariable Long taskId,@PathVariable Status status) {
        phaseService.updateTaskStatus(taskId, status);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Move task to backlog",
            description = "This API will move task to backlog.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Move task to backlog successfully"),
            })
    @PatchMapping("{projectId}/{phaseId}/backlog/{taskId}")
    public ResponseEntity<?> moveTaskToBacklog(@PathVariable Long projectId, @PathVariable Long phaseId, @PathVariable Long taskId) {
        phaseService.moveTaskToBacklog(taskId);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.PHASE_MOVE_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }
}
