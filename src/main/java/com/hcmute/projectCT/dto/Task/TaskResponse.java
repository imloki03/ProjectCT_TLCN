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
public class TaskResponse {
    Long id;
    String name;
    TaskType type;
    String description;
    LocalDateTime startTime;
    LocalDateTime endTime;
    Priority priority;
    List<Long> subTaskIds;
    Long parentTaskId;
    String assigneeUsername;
    Status status;
    Long backlogId;


    public TaskResponse(Task task){
        this.id = task.getId();
        this.name = task.getName();
        this.type = task.getType();
        this.description = task.getDescription();
        this.startTime = task.getStartTime();
        this.endTime = task.getEndTime();
        this.priority = task.getPriority();
        this.subTaskIds = task.getSubtaskIdList();
        this.parentTaskId = task.getParentTask().getId();
        this.assigneeUsername = task.getAssignee().getUser().getUsername();
        this.status = task.getStatus();
        this.backlogId = task.getBacklog().getId();
    }
}
