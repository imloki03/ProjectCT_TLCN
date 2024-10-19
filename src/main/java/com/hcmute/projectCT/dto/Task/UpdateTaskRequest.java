package com.hcmute.projectCT.dto.Task;

import com.hcmute.projectCT.enums.Priority;
import com.hcmute.projectCT.enums.Status;
import com.hcmute.projectCT.enums.TaskType;
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
public class UpdateTaskRequest {
    String name;
    TaskType type;
    String description;
    LocalDateTime startTime;
    LocalDateTime endTime;
    private Priority priority;
    String assigneeUsername;
    Status status;

    public UpdateTaskRequest(Task task){
        this.name = task.getName();
        this.type = task.getType();
        this.description = task.getDescription();
        this.startTime = task.getStartTime();
        this.endTime = task.getEndTime();
        this.priority = task.getPriority();
        this.assigneeUsername = task.getAssignee().getUsername();
        this.status = task.getStatus();
    }
}
