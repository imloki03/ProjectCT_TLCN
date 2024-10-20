package com.hcmute.projectCT.dto.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditProfileRequest {

    @Schema(description = "Updated name of the user", example = "John Doe")
    private String name;

    @Schema(description = "Updated gender of the user", example = "Male")
    private String gender;


    @Schema(description = "List of tag IDs associated with the user", example = "[1, 2, 3]")
    private List<Long> tagList;
}
