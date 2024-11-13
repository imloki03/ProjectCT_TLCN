package com.hcmute.projectCT.service;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.Media.MediaRequest;
import com.hcmute.projectCT.dto.Media.MediaResponse;
import com.hcmute.projectCT.enums.MediaType;
import com.hcmute.projectCT.exception.InternalServerException;
import com.hcmute.projectCT.model.MediaContent;
import com.hcmute.projectCT.model.Project;
import com.hcmute.projectCT.repository.MediaContentRepository;
import com.hcmute.projectCT.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {
    private final ProjectRepository projectRepository;
    private final MediaContentRepository mediaContentRepository;

    @Override
    public void addMedia(MediaRequest mediaRequest, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new InternalServerException(HttpStatus.NOT_FOUND.value(), MessageKey.PROJECT_NOT_FOUND));

        try {
            MediaContent mediaContent = toEntity(mediaRequest);
            mediaContent.setMedia(project.getMedia());
            mediaContentRepository.save(mediaContent);
        } catch (Exception e) {
            log.error("Error occurred while adding media content", e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public void editMedia(Long id, MediaRequest mediaRequest) {
        MediaContent existingMedia = mediaContentRepository.findById(id)
                .orElseThrow(() -> new InternalServerException(HttpStatus.NOT_FOUND.value(), MessageKey.MEDIA_NOT_FOUND));

        try {
            MediaContent updatedMedia = toEntity(existingMedia, mediaRequest);
            mediaContentRepository.save(updatedMedia);
        } catch (Exception e) {
            log.error("Error occurred while editing media content", e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public void deleteMedia(Long id) {
        MediaContent mediaContent = mediaContentRepository.findById(id)
                .orElseThrow(() -> new InternalServerException(HttpStatus.NOT_FOUND.value(), MessageKey.MEDIA_NOT_FOUND));

        try {
            mediaContentRepository.delete(mediaContent);
        } catch (Exception e) {
            log.error("Error occurred while deleting media content with ID: {}", id, e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public void updateMediaVersion(Long id, Long projectId, MediaRequest mediaRequest) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new InternalServerException(HttpStatus.NOT_FOUND.value(), MessageKey.PROJECT_NOT_FOUND));

        MediaContent previousMedia = mediaContentRepository.findById(id)
                .orElseThrow(() -> new InternalServerException(HttpStatus.NOT_FOUND.value(), MessageKey.MEDIA_NOT_FOUND));

        try {
            MediaContent newMediaVersion = toEntity(mediaRequest);
            newMediaVersion.setMedia(project.getMedia());
            newMediaVersion.setPreviousVersion(previousMedia);
            mediaContentRepository.save(newMediaVersion);
        } catch (Exception e) {
            log.error("Error occurred while updating media version for ID: {}", id, e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public MediaResponse getMediaInfo(Long id) {
        MediaContent mediaContent = mediaContentRepository.findById(id)
                .orElseThrow(() -> new InternalServerException(HttpStatus.NOT_FOUND.value(), MessageKey.MEDIA_NOT_FOUND));

        try {
            return new MediaResponse(mediaContent);
        } catch (Exception e) {
            log.error("Error occurred while fetching media info for ID: {}", id, e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public List<MediaResponse> getMediaByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new InternalServerException(HttpStatus.NOT_FOUND.value(), MessageKey.PROJECT_NOT_FOUND));

        try {
            List<MediaContent> mediaList = mediaContentRepository.findByMedia(project.getMedia());
            return mediaList.stream().map(MediaResponse::new).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while fetching media for project ID: {}", projectId, e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public MediaContent toEntity(MediaRequest mediaRequest) {
        return MediaContent.builder()
                .name(mediaRequest.getName())
                .description(mediaRequest.getDescription())
                .filename(mediaRequest.getFilename())
                .link(mediaRequest.getLink())
                .type(convertFilenameToMediaType(mediaRequest.getFilename()))
                .uploadTime(LocalDateTime.now())
                .build();
    }

    @Override
    public MediaContent toEntity(MediaContent existingMedia, MediaRequest mediaRequest) {
        return existingMedia.toBuilder()
                .name(Optional.ofNullable(mediaRequest.getName()).orElse(existingMedia.getName()))
                .description(Optional.ofNullable(mediaRequest.getDescription()).orElse(existingMedia.getDescription()))
                .filename(Optional.ofNullable(mediaRequest.getFilename()).orElse(existingMedia.getFilename()))
                .link(Optional.ofNullable(mediaRequest.getLink()).orElse(existingMedia.getLink()))
                .type(mediaRequest.getFilename() != null
                        ? convertFilenameToMediaType(mediaRequest.getFilename())
                        : existingMedia.getType())
                .build();
    }

    @Override
    public MediaType convertFilenameToMediaType(String filename) {
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        return switch (extension) {
            case "mp4", "avi", "mkv" -> MediaType.VIDEO;
            case "jpg", "jpeg", "png", "gif" -> MediaType.IMAGE;
            case "doc", "docx", "pdf" -> MediaType.DOC;
            case "ppt", "pptx" -> MediaType.PRESENTATION;
            case "xls", "xlsx" -> MediaType.WORKBOOK;
            default -> throw new IllegalArgumentException("Unsupported file type: " + extension);
        };
    }
}
