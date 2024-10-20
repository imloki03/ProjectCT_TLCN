package com.hcmute.projectCT.dto.Version;

import com.hcmute.projectCT.model.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VersionResponse {
    private String name;
    private String description;
    private LocalDateTime createdDate;
    private List<String> taskList;
}
