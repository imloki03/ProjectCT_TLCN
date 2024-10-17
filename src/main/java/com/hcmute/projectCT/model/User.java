package com.hcmute.projectCT.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String name;
    private String email;
    private String password;
    private String gender;

    @JsonIgnoreProperties("user")
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

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Notification> notificationList;

    @JsonIgnoreProperties("owner")
    @OneToMany(mappedBy = "owner")
    private List<Project> projectList;

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Collaborator> collaboratorList;

    @JsonIgnoreProperties("sender")
    @OneToMany(mappedBy = "sender", cascade = CascadeType.REMOVE)
    private List<Message> messageList;

    @JsonIgnoreProperties("assignee")
    @OneToMany(mappedBy = "assignee")
    private List<Task> taskList;
}
