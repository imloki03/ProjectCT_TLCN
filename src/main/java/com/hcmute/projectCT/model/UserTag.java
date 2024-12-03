package com.hcmute.projectCT.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_tags")
public class UserTag {
    @EmbeddedId
    private UserTagId id;

    @ManyToOne
    @MapsId("userId") // Maps this part of the composite key
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("tagId") // Maps this part of the composite key
    @JoinColumn(name = "tag_id")
    private Tag tag;

    // Getters and setters
}
