package com.hcmute.projectCT.controller;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.RespondData;
import com.hcmute.projectCT.dto.Task.TaskRequest;
import com.hcmute.projectCT.dto.Task.TaskResponse;
import com.hcmute.projectCT.dto.Task.UpdateTaskRequest;
import com.hcmute.projectCT.model.Phase;
import com.hcmute.projectCT.repository.PhaseRepository;
import com.hcmute.projectCT.service.BacklogService;
import com.hcmute.projectCT.util.MessageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/backlog")
@RequiredArgsConstructor
@Tag(name = "Backlog Controller")
public class BacklogController {
    final BacklogService backlogService;
    final PhaseRepository phaseRepository;
    final MessageUtil messageUtil;

    @Operation(
            summary = "Create new task",
            description = "This API will create new task.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Task created successfully"),
            })
    @PostMapping("{projectId}")
    public ResponseEntity<?> createNewTask(@PathVariable Long projectId, @RequestBody TaskRequest taskRequest){
        backlogService.createNewTask(projectId, taskRequest);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.TASK_CREATE_SUCCESS, taskRequest.getType()))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Get task",
            description = "This API will get a task.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get task successfully"),
            })
    @GetMapping("{projectId}/{taskId}")
    public ResponseEntity<?> getTask(@PathVariable Long projectId, @PathVariable Long taskId){
        TaskResponse task = backlogService.getTask(taskId);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .data(task)
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all tasks",
            description = "This API will get all tasks of a project.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get all tasks successfully"),
            })
    @GetMapping("{projectId}")
    public ResponseEntity<?> getAllTasksInBacklogs(@PathVariable Long projectId){
        List<TaskResponse> taskResponses = backlogService.getAllTasksInBacklogs(projectId);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .data(taskResponses)
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Update task",
            description = "This API will update a task information.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Task updated successfully"),
            })
    @PutMapping("{projectId}/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long projectId, @PathVariable Long taskId, @RequestBody UpdateTaskRequest updateTaskRequest) throws MessagingException, IOException {
        backlogService.updateTask(projectId, taskId, updateTaskRequest);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.TASK_UPDATE_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Move task to phase",
            description = "This API will move a task to a phase.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Task moved to phase successfully"),
            })
    @PatchMapping("{taskId}/{phaseId}")
    public ResponseEntity<?> moveTaskToPhase(@PathVariable Long taskId, @PathVariable Long phaseId){
        backlogService.moveTaskToPhase(taskId, phaseId);
        Phase phase = phaseRepository.findById(phaseId).orElse(null);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.TASK_MOVE_TO_PHASE_SUCCESS, phase.getName()))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete task",
            description = "This API will delete a task.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Task deleted successfully"),
            })
    @DeleteMapping("{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId){
        backlogService.deleteTask(taskId);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.TASK_DELETE_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }
}
