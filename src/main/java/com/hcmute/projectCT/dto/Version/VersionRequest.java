package com.hcmute.projectCT.dto.Version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VersionRequest {
    private String name;
    private String description;
    private List<Long> targetedTaskList;
}
