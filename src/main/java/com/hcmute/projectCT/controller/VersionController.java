package com.hcmute.projectCT.controller;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.RespondData;
import com.hcmute.projectCT.dto.Version.VersionRequest;
import com.hcmute.projectCT.dto.Version.VersionResponse;
import com.hcmute.projectCT.exception.InternalServerException;
import com.hcmute.projectCT.service.VersionService;
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
@RequestMapping("/api/v1/version")
@RequiredArgsConstructor
@Tag(name = "Version Controller")
public class VersionController {
    private final VersionService versionService;
    private final MessageUtil messageUtil;

    @Operation(
            summary = "Create a new version",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Version created successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping("/project/{projectId}")
    public ResponseEntity<?> createVersion(
            @Parameter(description = "ID of the project for the new version", example = "1")
            @PathVariable Long projectId,
            @RequestBody VersionRequest versionRequest) {
        try {
            versionService.createVersion(versionRequest, projectId);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.VERSION_CREATED_SUCCESS))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        } catch (InternalServerException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Edit an existing version",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Version updated successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> editVersion(
            @Parameter(description = "ID of the version to edit", example = "2")
            @PathVariable Long id,
            @RequestBody VersionRequest versionRequest) {
        try {
            versionService.editVersion(id, versionRequest);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.VERSION_UPDATED_SUCCESS))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        } catch (InternalServerException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Delete a version",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Version deleted successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVersion(
            @Parameter(description = "ID of the version to delete", example = "3")
            @PathVariable Long id) {
        try {
            versionService.deleteVersion(id);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.VERSION_DELETED_SUCCESS))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        } catch (InternalServerException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get version by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Version retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Version not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getVersionById(
            @Parameter(description = "ID of the version to retrieve", example = "4")
            @PathVariable Long id) {
        try {
            VersionResponse versionResponse = versionService.getVersionById(id);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.VERSION_FETCH_SUCCESS))
                    .data(versionResponse)
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        } catch (InternalServerException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get all versions for a project",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Versions retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Project not found")
            }
    )
    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getVersionsByProject(
            @Parameter(description = "ID of the project to retrieve versions for", example = "1")
            @PathVariable Long projectId) {
        try {
            List<VersionResponse> versions = versionService.getVersionsByProject(projectId);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.VERSION_FETCH_SUCCESS))
                    .data(versions)
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        } catch (InternalServerException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
