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
    Long phaseId;


    public TaskResponse(Task task){
        this.id = task.getId();
        this.name = task.getName();
        this.type = task.getType();
        this.description = task.getDescription();
        this.startTime = task.getStartTime();
        this.endTime = task.getEndTime();
        this.priority = task.getPriority();
        this.subTaskIds = task.getSubtaskIdList();
        if (task.getParentTask()!=null){
            this.parentTaskId = task.getParentTask().getId();
        }
        if (task.getAssignee()!=null){
            this.assigneeUsername = task.getAssignee().getUser().getUsername();
        }
        this.status = task.getStatus();
        if (task.getBacklog()!=null){
            this.backlogId = task.getBacklog().getId();
        }
        if (task.getPhase()!=null){
            this.phaseId = task.getPhase().getId();
        }
    }
}
