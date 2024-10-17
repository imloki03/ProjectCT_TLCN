package com.hcmute.projectCT.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hcmute.projectCT.enums.Priority;
import com.hcmute.projectCT.enums.Status;
import com.hcmute.projectCT.enums.TaskType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    @JsonIgnoreProperties("parentTask")
    @OneToMany(mappedBy = "parentTask", orphanRemoval = true)
    private List<Task> subTask;

    @JsonIgnoreProperties("subTask")
    @ManyToOne
    @JoinColumn(name = "parent_task_id")
    private Task parentTask;

    @JsonIgnoreProperties("taskList")
    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @Enumerated(EnumType.STRING)
    private Status status;

    private boolean isBacklog;

    @JsonIgnoreProperties("task")
    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE)
    private List<MediaContent> mediaList;

    @JsonIgnoreProperties("taskList")
    @ManyToOne
    private Phase phase;

    @JsonIgnoreProperties("taskList")
    @ManyToOne
    private Version version;
}
