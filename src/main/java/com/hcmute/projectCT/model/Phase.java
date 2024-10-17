package com.hcmute.projectCT.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hcmute.projectCT.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Phase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "phase")
    @JsonIgnoreProperties("phase")
    private List<Task> taskList;

    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties("phaseList")
    private Project project;
}
