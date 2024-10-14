package com.hcmute.projectCT.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;
    private String name;
    private String email;
    private String password;

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private UserStatus status;

    private String avatarURL;

    @JsonIgnoreProperties("users")
    @ManyToMany
    @JoinTable(
            name = "user_tags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tagList;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Notification> notificationList;

    @JsonManagedReference
    @OneToMany(mappedBy = "owner")
    private List<Project> projectList;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Collaborator> collaboratorList;

    @JsonManagedReference
    @OneToMany(mappedBy = "sender", cascade = CascadeType.REMOVE)
    private List<Message> messageList;

    @JsonBackReference
    @OneToMany(mappedBy = "assignee")
    private List<Task> task;
}
