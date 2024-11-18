package com.hcmute.projectCT.dto.User;

import com.hcmute.projectCT.dto.Project.ProjectResponse;
import com.hcmute.projectCT.dto.Task.TaskResponse;
import com.hcmute.projectCT.model.Project;
import com.hcmute.projectCT.model.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllAssignedTaskResponse {
    ProjectResponse project;
    List<TaskResponse> tasks;

    public AllAssignedTaskResponse(List<Task> task, Project project) {
        this.tasks = Objects.requireNonNull(task).stream()
                .map(TaskResponse::new)
                .toList();
        this.project = new ProjectResponse(project);
    }
}
