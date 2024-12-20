package com.hcmute.projectCT.controller;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.AuthResponse;
import com.hcmute.projectCT.dto.RespondData;
import com.hcmute.projectCT.dto.Token;
import com.hcmute.projectCT.dto.User.LoginRequest;
import com.hcmute.projectCT.dto.User.UserResponse;
import com.hcmute.projectCT.exception.LoginFailedException;
import com.hcmute.projectCT.service.AuthService;
import com.hcmute.projectCT.util.MessageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller")
public class AuthController {
    final AuthService authService;
    final MessageUtil messageUtil;
    @Operation(
            summary = "Login",
            description = "This API will check and login for user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login successfully"),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Username or password is incorrect"),
            })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse authResponse = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
            var respondData = RespondData
                    .builder()
                    .status(HttpStatus.OK.value())
                    .data(authResponse.getData())
                    .desc(messageUtil.getMessage(MessageKey.LOGIN_SUCCESS))
                    .token(authResponse.getToken())
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        } catch (LoginFailedException e){
            var respondData = RespondData
                    .builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh() {
        try {
            Token token = authService.generateToken();
            var respondData = RespondData
                    .builder()
                    .status(HttpStatus.OK.value())
                    .data(token)
                    .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        } catch (RuntimeException e) {
            var respondData = RespondData
                    .builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .desc(e.getMessage())
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.UNAUTHORIZED);
        }
    }
}
