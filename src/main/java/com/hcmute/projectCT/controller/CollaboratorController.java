package com.hcmute.projectCT.controller;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.Collaborator.CollaboratorResponse;
import com.hcmute.projectCT.dto.RespondData;
import com.hcmute.projectCT.dto.Task.TaskResponse;
import com.hcmute.projectCT.enums.Permission;
import com.hcmute.projectCT.exception.AddCollabFailedException;
import com.hcmute.projectCT.model.Collaborator;
import com.hcmute.projectCT.service.CollaboratorService;
import com.hcmute.projectCT.util.MessageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("api/v1/collab")
@RequiredArgsConstructor
@Tag(name = "Collaborator Controller")
public class CollaboratorController {
    final CollaboratorService collaboratorService;
    final MessageUtil messageUtil;

    @Operation(
            summary = "Add new collaborator",
            description = "This API will add new collaborator for a project.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Add collaborator successfully"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Duplicated collaborator"),
            })
    @PostMapping("{projectId}/{username}")
    public ResponseEntity<?> addNewCollaborator(@PathVariable Long projectId, @PathVariable String username) {
        try {
            collaboratorService.addNewCollaborator(projectId, username);
            var respondData = RespondData
                    .builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.COLLAB_ADD_SUCCESS, username))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        } catch (AddCollabFailedException e) {
            var respondData = RespondData
                    .builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage(), username))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
    }


    @Operation(
            summary = "Get all collaborators",
            description = "This API will get all collaborators for a project.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get all collaborators successfully"),
            })
    @GetMapping("{projectId}")
    public ResponseEntity<?> getAllCollaborator(@PathVariable Long projectId) {
        List<CollaboratorResponse> collaboratorResponses = collaboratorService.getAllCollaborators(projectId);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .data(collaboratorResponses)
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Update collaborator permission",
            description = "This API will update collaborator's permission.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update collaborator's permission successfully"),
            })
    @PatchMapping("{projectId}/{collabId}/{permission}")
    public ResponseEntity<?> updateCollabPermission(@PathVariable Long projectId, @PathVariable Long collabId, @PathVariable Permission permission) {
        collaboratorService.updateCollabPermission(collabId, permission);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete collaborator",
            description = "This API will delete collaborator.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Delete collaborator successfully"),
            })
    @DeleteMapping("{projectId}/{collabId}")
    public ResponseEntity<?> deleteCollaborator(@PathVariable Long projectId, @PathVariable Long collabId) {
        collaboratorService.deleteCollaborator(collabId);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.COLLAB_DELETE_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Get collaborator all assigned task",
            description = "This API will get collaborator all assigned task.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get collaborator assigned task successfully"),
            })
    @GetMapping("{collabId}/tasks")
    public ResponseEntity<?> getAllCollaboratorAssignedTask(@PathVariable Long collabId) {
        List<TaskResponse> tasks = collaboratorService.getAllCollaboratorAssignedTask(collabId);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .data(tasks)
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }
}
