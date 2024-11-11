package com.hcmute.projectCT.dto.Version;

import com.hcmute.projectCT.dto.Task.TaskResponse;
import com.hcmute.projectCT.model.Task;
import com.hcmute.projectCT.model.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VersionResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdDate;
    private List<TaskResponse> taskList;

    public VersionResponse(Version version) {
        this.id = version.getId();
        this.name = version.getName();
        this.description = version.getDescription();
        this.createdDate = version.getCreatedDate();
        this.taskList = Optional.ofNullable(version.getTaskList())
                .map(tasks -> tasks.stream()
                        .map(TaskResponse::new)
                        .collect(Collectors.toList()))
                .orElse(null);
    }
}
