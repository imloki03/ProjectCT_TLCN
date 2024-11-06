package com.hcmute.projectCT.controller;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.Project.ProjectResponse;
import com.hcmute.projectCT.dto.Project.UpdateProjectImageRequest;
import com.hcmute.projectCT.dto.Project.UpdateProjectRequest;
import com.hcmute.projectCT.dto.RespondData;
import com.hcmute.projectCT.model.Project;
import com.hcmute.projectCT.service.ProjectService;
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
@RequestMapping("api/v1/project")
@RequiredArgsConstructor
@Tag(name = "Project Controller")
public class ProjectController {
    final ProjectService projectService;
    final MessageUtil messageUtil;

    @Operation(
            summary = "Create new project",
            description = "This API will create new project.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Project created successfully"),
            })
    @PostMapping("{owner}")
    public ResponseEntity<?> createNewProject(@Parameter(description = "Owner username")
                                              @PathVariable String owner,
                                              @RequestParam String name,
                                              @RequestParam String description){
        ProjectResponse projectResponse = projectService.createNewProject(owner, name, description);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .data(projectResponse)
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Get project",
            description = "This API will get a project information by its ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get project successfully"),
            })
    @GetMapping("{owner}/{id}")
    public ResponseEntity<?> getProject(@Parameter(description = "Owner username")
                                        @PathVariable String owner,
                                        @Parameter(description = "Project ID")
                                        @PathVariable Long id){
        ProjectResponse project = projectService.getProject(id);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .data(project)
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all project",
            description = "This API will get all project information of a user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get all projects successfully"),
            })
    @GetMapping("{owner}")
    public ResponseEntity<?> getAllProject(@Parameter(description = "Owner username")
                                           @PathVariable String owner){
        List<ProjectResponse> projects = projectService.getAllProjectForUser(owner);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .data(projects)
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Update project image",
            description = "This API will update project image for a project.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update project successfully"),
            })
    @PatchMapping("{id}")
    public ResponseEntity<?> updateProjectAvatar(@Parameter(description = "Project ID")
                                                 @PathVariable Long id,
                                                 @RequestBody UpdateProjectImageRequest updateProjectImageRequest){
        projectService.updateProjectAvatar(id, updateProjectImageRequest.getImageUrl());
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.PROJECT_IMAGE_UPDATE_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Update project information",
            description = "This API will update project information for a project.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update project successfully"),
            })
    @PutMapping("{id}")
    public ResponseEntity<?> updateProjectInformation(@Parameter(description = "Project ID")
                                                      @PathVariable Long id,
                                                      @RequestBody UpdateProjectRequest info){
        projectService.updateProjectInformation(id, info);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.PROJECT_INFO_UPDATE_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete project",
            description = "This API will delete a project.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Delete project successfully"),
            })
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProject(@Parameter(description = "Project ID")
                                           @PathVariable Long id){
        projectService.deleteProject(id);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.PROJECT_DELETE_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }
}
