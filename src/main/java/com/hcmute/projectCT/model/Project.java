package com.hcmute.projectCT.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private String name;
    private String description;
    private String urlName;
    private String gender;
    private String avatarURL;
    private LocalDateTime createdDate;

    @JsonManagedReference
    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<Phase> phaseList;

    @JsonManagedReference
    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<Message> messageList;

    @JsonManagedReference
    @OneToOne(mappedBy = "project", cascade = CascadeType.REMOVE)
    private Media media;

    @JsonManagedReference
    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<Collaborator> collaboratorList;
}
