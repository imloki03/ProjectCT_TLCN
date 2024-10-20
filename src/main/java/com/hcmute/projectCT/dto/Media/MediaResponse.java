package com.hcmute.projectCT.dto.Media;

import com.hcmute.projectCT.enums.MediaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
}
