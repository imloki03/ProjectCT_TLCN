package com.hcmute.projectCT.dto.Media;

import com.hcmute.projectCT.enums.MediaType;
import com.hcmute.projectCT.model.MediaContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaResponse {
    private String name;
    private String description;
    private LocalDateTime uploadTime;
    private String type;
    private String filename;
    private String link;
    private Long previousVersion;

    public MediaResponse(MediaContent mediaContent) {
        this.name = mediaContent.getName();
        this.description = mediaContent.getDescription();
        this.uploadTime = mediaContent.getUploadTime();
        this.type = mediaContent.getType().name();
        this.filename = mediaContent.getFilename();
        this.link = mediaContent.getLink();
        this.previousVersion = Optional.ofNullable(mediaContent.getPreviousVersion())
                .map(MediaContent::getId)
                .orElse(null);
    }
}