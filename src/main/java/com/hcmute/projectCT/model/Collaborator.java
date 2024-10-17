package com.hcmute.projectCT.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hcmute.projectCT.enums.Permission;
import jakarta.persistence.*;
import lombok.*;


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
    @JsonIgnoreProperties("collaboratorList")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties("collaboratorList")
    private Project project;

    @Enumerated(EnumType.STRING)
    private Permission permission;
}
