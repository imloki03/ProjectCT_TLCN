package com.hcmute.projectCT.dto.Task;

import com.hcmute.projectCT.enums.Priority;
import com.hcmute.projectCT.enums.TaskType;
import com.hcmute.projectCT.model.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    String name;
    TaskType type;
    String description;
    Priority priority;
    Long parentTaskId;

    public TaskRequest(Task task){
        this.name = task.getName();
        this.type = task.getType();
        this.description = task.getDescription();
        this.priority = task.getPriority();
        this.parentTaskId = task.getParentTask().getId();
    }
}
