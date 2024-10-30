package com.hcmute.projectCT.dto.User;

import com.hcmute.projectCT.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagResponse {
    private Long id;
    private String name;
    private String type;
    private String description;

    public TagResponse(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
        this.type = tag.getType().toString();
        this.description = tag.getDescription();
    }
}
