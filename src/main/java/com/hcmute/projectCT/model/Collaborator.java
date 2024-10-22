package com.hcmute.projectCT.model;

import com.fasterxml.jackson.annotation.*;
import com.hcmute.projectCT.enums.Permission;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Collaborator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private Permission permission;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.REMOVE)
    private List<Message> messageList;

    @OneToMany(mappedBy = "assignee")
    private List<Task> taskList;
}
