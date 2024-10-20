package com.hcmute.projectCT.dto.Media;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaRequest {

    @Schema(description = "Name of the media item", example = "Project Design Document")
    private String name;

    @Schema(description = "Brief description of the media item", example = "Design documents for version 1.0")
    private String description;

    @Schema(description = "Name of the uploaded file", example = "design_doc_v1.pdf")
    private String filename;

    @Schema(description = "Link to access the media", example = "https://example.com/media/12345")
    private String link;
}
