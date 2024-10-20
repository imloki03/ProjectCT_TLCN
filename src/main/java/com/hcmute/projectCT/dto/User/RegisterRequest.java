package com.hcmute.projectCT.dto.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @Schema(description = "Username for registration", example = "johndoe123")
    private String username;

    @Schema(description = "Full name of the user", example = "John Doe")
    private String name;

    @Schema(description = "Password for the user", example = "password123")
    private String password;

    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Gender of the user", example = "Male")
    private String gender;
}
