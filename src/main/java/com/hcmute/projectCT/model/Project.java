package com.hcmute.projectCT.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private String name;
    private String description;
    private String urlName;
    private String gender;
    private String avatarURL;
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<Phase> phaseList;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<Message> messageList;

    @OneToOne(mappedBy = "project", cascade = CascadeType.REMOVE)
    private Media media;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<Collaborator> collaboratorList;
}
