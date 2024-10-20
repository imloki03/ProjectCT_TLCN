package com.hcmute.projectCT.dto.Media;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaRequest {
    private String name;
    private String description;
    private String filename;
    private String link;
}
