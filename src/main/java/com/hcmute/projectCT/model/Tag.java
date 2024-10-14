package com.hcmute.projectCT.model;

import com.hcmute.projectCT.enums.TagType;
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
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TagType type;

    private String description;

    @ManyToMany(mappedBy = "tagList")
    private List<User> users;
}
