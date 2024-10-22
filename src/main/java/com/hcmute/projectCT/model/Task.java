package com.hcmute.projectCT.model;

import com.fasterxml.jackson.annotation.*;
import com.hcmute.projectCT.enums.Priority;
import com.hcmute.projectCT.enums.Status;
import com.hcmute.projectCT.enums.TaskType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @OneToMany(mappedBy = "parentTask", orphanRemoval = true)
    private List<Task> subTask;

    @ManyToOne
    @JoinColumn(name = "parent_task_id")
    private Task parentTask;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private Collaborator assignee;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE)
    private List<MediaContent> mediaList;

    @ManyToOne
    private Backlog backlog;

    @ManyToOne
    private Phase phase;

    @ManyToOne
    private Version version;

    public List<Long> getSubtaskIdList(){
        List<Long> subtaskId = new ArrayList<>();
        for (Task task : subTask) {
            subtaskId.add(task.getId());
        }
        return subtaskId;
    }
}
