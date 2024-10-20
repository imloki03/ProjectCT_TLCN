package com.hcmute.projectCT.controller;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.Media.MediaRequest;
import com.hcmute.projectCT.dto.Media.MediaResponse;
import com.hcmute.projectCT.dto.RespondData;
import com.hcmute.projectCT.exception.InternalServerException;
import com.hcmute.projectCT.service.MediaService;
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
@RequestMapping("/api/v1/media")
@RequiredArgsConstructor
@Tag(name = "Media Controller")
public class MediaController {
    private final MediaService mediaService;
    private final MessageUtil messageUtil;

    @Operation(
            summary = "Add new media",
            description = "This API adds a new media file to the specified project",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Media added successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PostMapping("/project/{projectId}")
    public ResponseEntity<?> addMedia(
            @Parameter(description = "ID of the project to which the media will be added")
            @PathVariable Long projectId,
            @RequestBody MediaRequest mediaRequest) {
        try {
            mediaService.addMedia(mediaRequest, projectId);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.MEDIA_ADDED_SUCCESS))
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
            summary = "Edit media profile",
            description = "This API updates the profile of an existing media file",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Media edited successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PutMapping("/{id}")
    public ResponseEntity<?> editMedia(
            @Parameter(description = "ID of the media to be edited")
            @PathVariable Long id,
            @RequestBody MediaRequest mediaRequest) {
        try {
            mediaService.editMedia(id, mediaRequest);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.MEDIA_EDITED_SUCCESS))
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
            summary = "Delete media",
            description = "This API deletes an existing media file by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Media deleted successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMedia(
            @Parameter(description = "ID of the media to be deleted")
            @PathVariable Long id) {
        try {
            mediaService.deleteMedia(id);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.MEDIA_DELETED_SUCCESS))
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
            summary = "Update media version",
            description = "This API updates the version of a media file for a specific project",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Media version updated successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PatchMapping("/{id}/project/{projectId}")
    public ResponseEntity<?> updateMediaVersion(
            @Parameter(description = "ID of the media to be updated")
            @PathVariable Long id,
            @Parameter(description = "ID of the project associated with the new version")
            @PathVariable Long projectId,
            @RequestBody MediaRequest mediaRequest) {
        try {
            mediaService.updateMediaVersion(id, projectId, mediaRequest);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.MEDIA_VERSION_UPDATED_SUCCESS))
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
            summary = "Get media information",
            description = "This API retrieves information of a specific media file by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Media information retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Media not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @GetMapping("/{id}")
    public ResponseEntity<?> getMediaInfo(
            @Parameter(description = "ID of the media file to retrieve")
            @PathVariable Long id) {
        try {
            MediaResponse mediaResponse = mediaService.getMediaInfo(id);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.MEDIA_FETCH_SUCCESS))
                    .data(mediaResponse)
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
            summary = "Get media by project",
            description = "This API retrieves all media files associated with a specific project ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Media list retrieved successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getMediaByProject(
            @Parameter(description = "ID of the project whose media files will be fetched")
            @PathVariable Long projectId) {
        try {
            List<MediaResponse> mediaList = mediaService.getMediaByProject(projectId);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.MEDIA_FETCH_SUCCESS))
                    .data(mediaList)
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
