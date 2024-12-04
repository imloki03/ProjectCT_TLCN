package com.hcmute.projectCT.controller;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.RespondData;
import com.hcmute.projectCT.dto.User.*;
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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "APIs for user-related operations.")
public class UserController {
    private final UserService userService;
    private final MessageUtil messageUtil;

    @Operation(
            summary = "Register Account",
            description = "This API registers a new user account.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully."),
                    @ApiResponse(responseCode = "400", description = "Registration failed due to duplicate username or email."),
                    @ApiResponse(responseCode = "500", description = "Internal server error.")
            }
    )
    @PostMapping("")
    public ResponseEntity<?> registerAccount(@RequestBody RegisterRequest registerRequest) {
        try {
            userService.register(registerRequest);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.REGISTER_SUCCESS))
                    .build();

            return new ResponseEntity<>(respondData, HttpStatus.OK);
        } catch (RegistrationException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        } catch (InternalServerException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Edit User Profile",
            description = "This API allows a user to edit their profile information.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profile edited successfully."),
                    @ApiResponse(responseCode = "500", description = "Internal server error.")
            }
    )
    @PutMapping("/{username}")
    public ResponseEntity<?> editProfile(
            @RequestBody EditProfileRequest editProfileRequest,
            @Parameter(description = "The username of the user to be edited.")
            @PathVariable String username) {
        try {
            userService.editProfile(editProfileRequest, username);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.EDIT_PROFILE_SUCCESS))
                    .build();

            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
        catch (RegistrationException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
        catch (InternalServerException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Edit User Avatar",
            description = "This API allows a user to update their avatar.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Avatar updated successfully."),
                    @ApiResponse(responseCode = "500", description = "Internal server error.")
            }
    )
    @PutMapping("/ava/{username}")
    public ResponseEntity<?> editProfileAvatar(
            @RequestBody EditUserAvatarRequest request,
            @Parameter(description = "The username of the user to update the avatar.")
            @PathVariable String username) {
        try {
            userService.editProfileAvatar(request, username);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.EDIT_PROFILE_SUCCESS))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
        catch (RegistrationException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
        catch (InternalServerException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Change User Passwrd",
            description = "This API allows a user to change password when the forgoted.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Change password successfully."),
                    @ApiResponse(responseCode = "500", description = "Internal server error.")
            }
    )
    @PatchMapping("/{username}")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            @Parameter(description = "The username of the user to be edited.")
            @PathVariable String username) {
        try {
            userService.changePassword(request, username);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.CHANGE_PASSWORD_SUCCESS))
                    .build();

            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
        catch (RegistrationException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
        catch (InternalServerException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Get User Information",
            description = "This API retrieves user information by username.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User information retrieved successfully."),
                    @ApiResponse(responseCode = "500", description = "Internal server error.")
            }
    )
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserInfo(
            @Parameter(description = "The username of the user to retrieve information.")
            @PathVariable String username) {
        try {
            UserResponse userResponse = userService.getUserInfo(username);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.USER_FETCH_SUCCESS))
                    .data(userResponse)
                    .build();

            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
        catch (RegistrationException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
        catch (InternalServerException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Activate User Account",
            description = "This API retrieves user information by username.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Activate successfully."),
                    @ApiResponse(responseCode = "500", description = "Internal server error.")
            }
    )
    @PatchMapping("/{username}/activate")
    public ResponseEntity<?> activateUserAccount(
            @Parameter(description = "The username of the user to activate.")
            @PathVariable String username) {
        try {
            userService.activateUser(username);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.USER_ACTIVATE_SUCCESS))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
        catch (RegistrationException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
        catch (InternalServerException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Introduce User ",
            description = "This API introduces user to the system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Introduce successfully."),
                    @ApiResponse(responseCode = "500", description = "Internal server error.")
            }
    )
    @PatchMapping("/{username}/introduce")
    public ResponseEntity<?> introduceUserAccount(
            @Parameter(description = "The username of the user to introduce.")
            @PathVariable String username) {
        try {
            userService.introduceUserAccount(username);
            var respondData = RespondData.builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
        catch (RegistrationException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
        catch (InternalServerException e) {
            var respondData = RespondData.builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUser(@RequestParam String q){
        UserResponse userResponse = userService.searchUserByExactlyUsernameOrEmail(q);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .data(userResponse)
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all task assigned to user all over system from all project",
            description = "This API get all task assigned to user all over system from all project.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Get tasks successfully."),
                    @ApiResponse(responseCode = "500", description = "Internal server error.")
            }
    )
    @GetMapping("/{username}/tasks")
    public ResponseEntity<?> getAllAssignedTaskAtAllProject(@Parameter(description = "The username of the user to activate.")
                                                            @PathVariable String username){
        List<AllAssignedTaskResponse> tasks = userService.getAllAssignedTaskAtAllProject(username);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .data(tasks)
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }
}
