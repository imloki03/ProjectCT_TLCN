package com.hcmute.projectCT.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties("projectList")
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private String name;
    private String description;
    private String urlName;
    private String gender;
    private String avatarURL;
    private LocalDateTime createdDate;

    @JsonIgnoreProperties("project")
    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<Phase> phaseList;

    @JsonIgnoreProperties("project")
    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<Message> messageList;

    @JsonIgnoreProperties("project")
    @OneToOne(mappedBy = "project", cascade = CascadeType.REMOVE)
    private Media media;

    @JsonIgnoreProperties("project")
    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<Collaborator> collaboratorList;
}
