package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.Media.MediaRequest;
import com.hcmute.projectCT.dto.Media.MediaResponse;
import com.hcmute.projectCT.enums.MediaType;
import com.hcmute.projectCT.model.MediaContent;

import java.util.List;

public interface MediaService {
    public void addMedia(MediaRequest mediaRequest, Long projectId);
    public void editMedia(Long id, MediaRequest mediaRequest);
    public void deleteMedia(Long id);
    public void updateMediaVersion(Long id, Long projectId, MediaRequest mediaRequest);
    public MediaResponse getMediaInfo(Long id);
    public List<MediaResponse> getMediaByProject(Long projectId);
    public MediaContent toEntity(MediaRequest mediaRequest);
    public MediaContent toEntity(MediaContent existingMedia, MediaRequest mediaRequest);
    public MediaResponse toResponse(MediaContent mediaContent);
    public MediaType convertFilenameToMediaType(String filename);
}
