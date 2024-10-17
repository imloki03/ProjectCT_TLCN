package com.hcmute.projectCT.model;

import com.fasterxml.jackson.annotation.*;
import com.hcmute.projectCT.enums.TagType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TagType type;

    private String description;

    @ManyToMany(mappedBy = "tagList")
    private List<User> users;
}
