package com.hcmute.projectCT.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class UserTagId implements Serializable {
    private Long userId;
    private Long tagId;

}
