package com.hcmute.projectCT.controller;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.RespondData;
import com.hcmute.projectCT.dto.User.EditProfileRequest;
import com.hcmute.projectCT.dto.User.RegisterRequest;
import com.hcmute.projectCT.exception.InternalServerException;
import com.hcmute.projectCT.exception.RegistrationException;
import com.hcmute.projectCT.service.UserService;
import com.hcmute.projectCT.util.MessageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Controller")
public class UserController {
    private final UserService userService;
    private final MessageUtil messageUtil;

    @Operation(
            summary = "Register Account",
            description = "This API registers a new user account.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully"),
                    @ApiResponse(responseCode = "400", description = "Registration failed due to duplicate username or email"),
                    @ApiResponse(responseCode = "500", description = "Internal server error, check for NULL, Data, ...")
            })
    @PostMapping("")
    public ResponseEntity<?> registerAccount(@RequestBody RegisterRequest registerRequest) {
        try {
            userService.register(registerRequest);
            var respondData = RespondData
                    .builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.REGISTER_SUCCESS))
                    .build();

            return new ResponseEntity<>(respondData, HttpStatus.OK);
        } catch (RegistrationException e) {
            var respondData = RespondData
                    .builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.BAD_REQUEST);
        } catch (InternalServerException e) {
            var respondData = RespondData
                    .builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Edit User Profile",
            description = "This API allows a user to edit their profile information.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profile edited successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error, check for NULL, Data, ...")
            })

    @PutMapping("/{username}")
    public ResponseEntity<?> editProfile(@RequestBody EditProfileRequest editProfileRequest,
                                         @Parameter(description = "The username of the user going to be edited")
                                         @PathVariable String username) {
        try {
            userService.editProfile(editProfileRequest, username);
            var respondData = RespondData
                    .builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.EDIT_PROFILE_SUCCESS))
                    .build();

            return new ResponseEntity<>(respondData, HttpStatus.OK);
        } catch (InternalServerException e) {
            var respondData = RespondData
                    .builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
